package com.github.ocm.mapping;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.ocm.annotations.CSVEntity;
import com.github.ocm.annotations.CSVField;
import com.github.ocm.annotations.CSVFieldIgnore;
import com.github.ocm.converters.ICSVTypeConverter;
import com.github.ocm.exceptions.ClassValidationException;
import com.github.ocm.exceptions.ConverterException;
import com.github.ocm.exceptions.ParsingException;

class MappingEngine {

	private static final Logger	logger	= Logger.getLogger(MappingEngine.class.getCanonicalName());

	Map<Class<?>, MappingRules>	classMapping;

	public MappingEngine() {
		classMapping = new HashMap<Class<?>, MappingRules>();
	}

	public void clearMapping() {
		classMapping.clear();
	}

	public void createMapping(Class<?> aClass) throws ClassValidationException {

		CSVEntity annotation = aClass.getAnnotation(CSVEntity.class);
		if (annotation == null) {
			logger.severe("Class " + aClass.getName() + " is not annotated with " + CSVEntity.class.getName() + " annotation");
			return;
		}

		if (!classMapping.containsKey(aClass)) {

			MappingRules mappingRules = new MappingRules();

			Field[] fields = aClass.getDeclaredFields();
			for (Field f : fields) {

				//omit ignored fields
				if(f.isAnnotationPresent(CSVFieldIgnore.class)) {
					continue;
				}

				String csvFieldName = f.getName();

				if (f.isAnnotationPresent(CSVField.class)) {
					String annotatedName = f.getAnnotation(CSVField.class).csvFieldName();
					if (!"".equals(annotatedName)) {
						csvFieldName = annotatedName;
					}
				}
				mappingRules.addMapping(f, csvFieldName);

				logger.log(Level.INFO, ("Added mapping class: " + aClass.getName() + ": " + f.getName() + " <-> " + csvFieldName));

			}

			classMapping.put(aClass, mappingRules);

		} else {
			logger.log(Level.WARNING, "Class {0} is already present in mapping", aClass.getName());
		}
	}

	public boolean isCSVHeaderValid(String[] csvHeader, MappingRules rules) {
		Set<String> classFieldNames = rules.getAllFieldsNames();

		for (String csvHeaderField : csvHeader) {
			if (!classFieldNames.contains(csvHeaderField)) {
				// one field is missing

				logger.severe("Field " + csvHeaderField + " is declared in entity class, but not found in CSV header");

				return false;
			}
		}
		return true;
	}

	<T> List<T> mapCSVToEntity(Class<T> aClass, File file) throws ParsingException, ConverterException {

		if (!classMapping.containsKey(aClass)) {
			throw new IllegalStateException("Class " + aClass.getName() + " is not mapped in engine, did you use auto discovery, or add this class mapping in configuration?");
		}

		List<T> ret = new LinkedList<T>();

		CSVEntity classAnnotation = aClass.getAnnotation(CSVEntity.class);
		String delimiter = classAnnotation.delimiter();
		String newLineSign = classAnnotation.newLineString();

		MappingRules fieldMappings = classMapping.get(aClass);

		CSVFile csv = CSVFile.openFileForReading(file, delimiter, newLineSign);
		csv.openFile();
		// header
		String[] header = csv.readNewLine();

		if (!isCSVHeaderValid(header, fieldMappings)) {
			throw new IllegalStateException("Header of the CSV file does not match declared entity (class: " + aClass.getName() + ", file: " + file + ") expecting all fields: " + classMapping.get(aClass).getAllFieldsNames());
		}

		String[] splittedLine = null;
		while ((splittedLine = csv.readNewLine()) != null) {
			try {
				T obj = aClass.newInstance();

				for (int i = 0; i < header.length; ++i) {
					String columnName = header[i];
					Field field = fieldMappings.getClassField(columnName);
					if (field != null) {

						if ("".equals(splittedLine[i])) {

							if (!fieldMappings.isNullable(field)) {
								throw new ParsingException("Found empty string in csv, but field " + field.getName() + " is marked as NOT NULLABLE");
							}

							splittedLine[i] = null;
						}

						Method setter = aClass.getDeclaredMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());
						ICSVTypeConverter<?> converter = fieldMappings.getConverter(field);

						try {
							setter.invoke(obj, converter.convertToType(splittedLine[i]));
						} catch (ConverterException ce) {
							if(ce.getStopProcessing()) {
								throw ce;
							} else {
								logger.log(Level.WARNING, "Could not convert: " + splittedLine[i] + " with converter: " + converter);
							}
						}

					}
				}

				ret.add(obj);

			}
			catch (InstantiationException ex) {
				throw new ParsingException("Could not instantiate entity class - make sure it contains accessible default constructor", ex);
			}
			catch (IllegalAccessException ex) {
				throw new ParsingException("Could not access entity gett/setter methods - make sure they are accessible", ex);
			}
			catch (SecurityException e) {
				throw new ParsingException("Could not access setter method in entity", e);
			}
			catch (NoSuchMethodException e) {
				throw new ParsingException("One of the setter methods in entity is missing", e);
			}
			catch (IllegalArgumentException e) {
				throw new ParsingException("Setter and parameter type does not match. Make sure that Converter returns the same type as field", e);
			}
			catch (InvocationTargetException e) {
				throw new ParsingException("Could not invoke setter", e);
			}
		}
		return ret;
	}

	void mapEntityToCSV(Collection<?> entities, File file) throws ParsingException, ClassValidationException, ConverterException {

		Class<?> aClass = entities.iterator().next().getClass();
		MappingRules mappingRules = classMapping.get(aClass);

		CSVEntity annotation = aClass.getAnnotation(CSVEntity.class);

		if (annotation == null) {
			throw new ClassValidationException("Class " + aClass.getName() + " is not annotated");
		}

		String delimiter = annotation.delimiter();
		String newLineSign = annotation.newLineString();

		CSVFile csv = CSVFile.openFileForWriting(file, delimiter, newLineSign);
		csv.openFile();

		String[] line = new String[mappingRules.getAllFieldsNames().size()];

		csv.writeLine(mappingRules.getAllFieldsNames().toArray(line));

		// no worry - this is linked set from linked hash map - order is ok
		Set<String> fields = mappingRules.getAllFieldsNames();

		for (Object entity : entities) {

			int indexer = 0;
			for (String field : fields) {
				Field f = mappingRules.getClassField(field);
				try {

					Method getter = aClass.getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), (Class<?>[]) null);
					Object fieldValue = getter.invoke(entity);

					ICSVTypeConverter<?> converter = mappingRules.getConverter(f);

					String convertedValue = "";

					if (fieldValue != null) {
						convertedValue = converter.convertFromType(fieldValue);
					}

					line[indexer++] = convertedValue;

				}
				catch (IllegalArgumentException e) {
					throw new ParsingException("Getter signature seems incorrect - it sould not take any parameters", e);
				}
				catch (IllegalAccessException e) {
					throw new ParsingException("Could not access entity gett/setter methods - make sure they are accessible", e);
				}
				catch (SecurityException e) {
					throw new ParsingException("Could not access getter method in entity", e);
				}
				catch (NoSuchMethodException e) {
					throw new ParsingException("One of the getter methods in entity is missing", e);
				}
				catch (InvocationTargetException e) {
					throw new ParsingException("Could not invoke getter", e);
				}
			}// for each field in class
			csv.writeLine(line);

		}// for each entity

		csv.closeFile();
	}

}
