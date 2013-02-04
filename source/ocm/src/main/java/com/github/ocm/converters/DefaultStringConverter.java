package com.github.ocm.converters;

import com.github.ocm.exceptions.ConverterException;

/**
 * This converter is used for fields with type of {@link String}.
 * 
 * @author Sebastian Laskawiec
 */
public final class DefaultStringConverter implements ICSVTypeConverter<String> {

	@Override
	public String convertFromType(Object type) throws ConverterException {
		return type.toString();
	}

	@Override
	public String convertToType(String csvField) throws ConverterException {
		return csvField;
	}

}
