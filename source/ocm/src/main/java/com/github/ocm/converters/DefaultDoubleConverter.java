package com.github.ocm.converters;

import com.github.ocm.exceptions.ConverterException;

/**
 * This converter is used for fields with type of {@link Double} and double
 * primitive.
 * 
 * @author Sebastian Laskawiec
 */
public class DefaultDoubleConverter implements ICSVTypeConverter<Double> {

	@Override
	public String convertFromType(Object type) throws ConverterException {
		if (type == null) {
			return "";
		} else {
			return ((Double) type).toString();
		}
	}

	@Override
	public Double convertToType(String csvField) throws ConverterException {
		if (csvField == null || "".equals(csvField)) {
			return null;
		} else {
			return Double.valueOf(csvField);
		}
	}

}
