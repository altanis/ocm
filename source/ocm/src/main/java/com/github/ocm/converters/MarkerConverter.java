package com.github.ocm.converters;

import com.github.ocm.exceptions.ConverterException;

/**
 * This is marker converter type. Due to JSR-175 definition
 * annotations must have a default parameter as compile-time
 * constant, which is not null. In this case this marker converter
 * is used. Do not try to use it. It is used in runtime to convert
 * primitive, for further details take a look at: <li>
 * {@link DefaultDoubleConverter}</li> <li>{@link DefaultFloatConverter}</li>
 * <li>{@link DefaultIntegerConverter}</li> <li>{@link DefaultStringConverter}</li>
 * 
 * @author slaskawi
 */
public final class MarkerConverter implements ICSVTypeConverter<MarkerConverter> {

	@Override
	public String convertFromType(Object type) throws ConverterException {
		throw new AssertionError();
	}

	@Override
	public MarkerConverter convertToType(String csvField) throws ConverterException {
		throw new AssertionError();
	}

}
