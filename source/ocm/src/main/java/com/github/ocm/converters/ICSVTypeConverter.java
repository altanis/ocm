package com.github.ocm.converters;

import java.util.Date;

import com.github.ocm.exceptions.ConverterException;

/**
 * Converter interface is used to convert text found in CSV to
 * custom object. Each type must proper converter. There are several
 * build-in converters for example: <li>{@link DefaultDoubleConverter}</li> <li>
 * {@link DefaultFloatConverter}</li> <li>{@link DefaultIntegerConverter}</li>
 * <li>{@link DefaultStringConverter}</li> <li>
 * {@link DefaultDateConverterYYYYMMDD}</li>
 * 
 * Each default converter supports auto boxing and unboxing. For example: <li>
 * {@link DefaultDoubleConverter} will support both {@link Double} and a double
 * primitive</li>
 * 
 * Mixing primitive and object type is prohibited (for example double type and
 * Double setter/getter).
 * 
 * @param <T>
 *            Object type after conversion, for example {@link Date} or any user
 *            type.
 * @author Sebastian Laskawiec
 */
public interface ICSVTypeConverter<T> {

	/**
	 * Converts user type object into {@link String}, which will be
	 * written into CSV file.
	 * 
	 * @param type
	 *            User object.
	 * @return User object converted into String.
	 * @throws ConverterException
	 *             This exception should be thrown if any converting errors
	 *             will occur. Note that
	 *             {@link ConverterException#ConverterException(Throwable)} is
	 *             designed
	 *             to wrap user type exceptions.
	 */
	String convertFromType(Object type) throws ConverterException;

	/**
	 * Converts {@link String} found in CSV into destination type.
	 * 
	 * @param csvField
	 *            String found in CSV.
	 * @return User object
	 * @throws ConverterException
	 *             This exception should be thrown if any converting errors
	 *             will occur. Note that
	 *             {@link ConverterException#ConverterException(Throwable)} is
	 *             designed
	 *             to wrap user type exceptions.
	 */
	T convertToType(String csvField) throws ConverterException;

}
