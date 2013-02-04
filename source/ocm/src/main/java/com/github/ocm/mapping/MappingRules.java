package com.github.ocm.mapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.ocm.annotations.CSVField;
import com.github.ocm.converters.DefaultDoubleConverter;
import com.github.ocm.converters.DefaultFloatConverter;
import com.github.ocm.converters.DefaultIntegerConverter;
import com.github.ocm.converters.DefaultStringConverter;
import com.github.ocm.converters.ICSVTypeConverter;
import com.github.ocm.converters.MarkerConverter;
import com.github.ocm.exceptions.ClassValidationException;

class MappingRules {

	volatile static HashMap<Class<?>, ICSVTypeConverter<?>>	defaultConverters;

	private static final Logger								logger	= Logger.getLogger(MappingRules.class.getCanonicalName());

	static {
		// reading is thread safe
		defaultConverters = new HashMap<Class<?>, ICSVTypeConverter<?>>();
		defaultConverters.put(Integer.class, new DefaultIntegerConverter());
		defaultConverters.put(String.class, new DefaultStringConverter());
		defaultConverters.put(Double.class, new DefaultDoubleConverter());
		defaultConverters.put(Float.class, new DefaultFloatConverter());
	}
	HashMap<Field, ICSVTypeConverter<?>>					converters;
	HashMap<String, Field>									csvToFieldMapping;
	HashMap<Field, String>									fieldToCSVMapping;

	HashMap<Field, Boolean>									nullable;

	public MappingRules() {
		fieldToCSVMapping = new LinkedHashMap<Field, String>();
		csvToFieldMapping = new LinkedHashMap<String, Field>();
		nullable = new LinkedHashMap<Field, Boolean>();
		converters = new LinkedHashMap<Field, ICSVTypeConverter<?>>();
	}

	public void addMapping(Field field, String csvField) throws ClassValidationException {
		if (fieldToCSVMapping.containsKey(field)) {
			logger.log(Level.WARNING, "Field: " + field + " already existing in mapping - is it duplicated? Ignoring");
			return;
		}

		if (csvToFieldMapping.containsKey(csvField)) {
			logger.log(Level.WARNING, "CSV Field: " + field + " already existing in mapping - is it duplicated? Ignoring");
			return;
		}

		CSVField fieldAnnotation = field.getAnnotation(CSVField.class);
		ICSVTypeConverter<?> converter = null;
		boolean nullable = true;
		if (fieldAnnotation != null) {

			nullable = fieldAnnotation.nullable();

			try {
				converter = fieldAnnotation.converter().newInstance();
			}
			catch (InstantiationException e) {
				throw new ClassValidationException("Unable to instanciate converter " + fieldAnnotation.converter().getName() + " make sure it contains default constructor", e);
			}
			catch (IllegalAccessException e) {
				throw new ClassValidationException("Could not access default constructor for converter " + fieldAnnotation.converter().getName(), e);
			}

		} else {
			converter = new MarkerConverter();
		}

		if (converters.containsKey(csvField)) {
			logger.log(Level.WARNING, "CSV Field: " + field + " already existing in mapping - is it duplicated? Ignoring");
			return;
		}

		this.nullable.put(field, nullable);
		converters.put(field, converter);
		fieldToCSVMapping.put(field, csvField);
		csvToFieldMapping.put(csvField, field);
	}

	public Set<String> getAllFieldsNames() {
		return csvToFieldMapping.keySet();
	}

	public Field getClassField(String csvField) {
		return csvToFieldMapping.get(csvField);
	}

	public ICSVTypeConverter<?> getConverter(Field field) {
		ICSVTypeConverter<?> ret = converters.get(field);
		// yep - marker interface, this is primitive, lets find a normal
		// converter for it
		if (ret.getClass() == MarkerConverter.class) {

			Class<?> queryType = field.getType();

			// here is a list auto boxed/unboxed converters.
			// basic concept is that if a bean has field, say double,
			// is should contain setter with argument double, but we can pass
			// Double there without any problems, if a null occur there, we
			// would
			// get reflection exception
			if (double.class.equals(queryType)) {
				queryType = Double.class;
			} else if (float.class.equals(queryType)) {
				queryType = Float.class;
			} else if (int.class.equals(queryType)) {
				queryType = Integer.class;
			}

			return MappingRules.defaultConverters.get(queryType);
		}
		return ret;
	}

	public String getCSVField(Field entityField) {
		return fieldToCSVMapping.get(entityField);
	}

	public boolean isNullable(Field field) {
		return nullable.get(field);
	}

}
