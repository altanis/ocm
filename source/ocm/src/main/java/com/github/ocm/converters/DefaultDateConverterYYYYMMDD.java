package com.github.ocm.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.ocm.exceptions.ConverterException;

/**
 * This converter is used for fields with type of {@link Date}. The only
 * supported format is YYYYMMDD
 *
 * @author Sebastian Laskawiec
 */
public class DefaultDateConverterYYYYMMDD implements ICSVTypeConverter<Date> {

    SimpleDateFormat dateFormatter;

    public DefaultDateConverterYYYYMMDD() {
        dateFormatter = new SimpleDateFormat("yyyyMMdd");
    }

    @Override
    public String convertFromType(Object type) {
        return dateFormatter.format((Date) type);
    }

    @Override
    public Date convertToType(String csvField) throws ConverterException {

        if (csvField == null) {
            return null;
        }

        try {
            return dateFormatter.parse(csvField);
        } catch (ParseException e) {
            throw new ConverterException("Could not parse date: " + csvField + " format does not fit: YYYYMMDD", e);
        }
    }

}
