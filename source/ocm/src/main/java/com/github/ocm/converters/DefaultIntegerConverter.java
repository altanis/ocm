package com.github.ocm.converters;

import com.github.ocm.exceptions.ConverterException;

/**
 * This converter is used for fields with type of {@link Integer} and int
 * primitive.
 * 
 * @author Sebastian Laskawiec
 */
public class DefaultIntegerConverter implements ICSVTypeConverter<Integer> {

	@Override
	public String convertFromType(Object type) throws ConverterException {
		if (type == null) {
			return "";
		} else {
			return ((Integer) type).toString();
		}
	}

	@Override
	public Integer convertToType(String csvField) throws ConverterException {
		if (csvField == null || "".equals(csvField)) {
			return null;
		} else {
			return Integer.valueOf(csvField);
		}
	}

}
