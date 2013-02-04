package com.github.ocm.converters;

import com.github.ocm.exceptions.ConverterException;

/**
 * This converter is used for fields with type of {@link Float} and float
 * primitive.
 * 
 * @author Sebastian Laskawiec
 */
public class DefaultFloatConverter implements ICSVTypeConverter<Float> {

	@Override
	public String convertFromType(Object type) throws ConverterException {
		if (type == null) {
			return "";
		} else {
			return ((Float) type).toString();
		}
	}

	@Override
	public Float convertToType(String csvField) throws ConverterException {
		if (csvField == null || "".equals(csvField)) {
			return null;
		} else {
			return Float.valueOf(csvField);
		}
	}

}
