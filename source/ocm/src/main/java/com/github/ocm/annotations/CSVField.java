package com.github.ocm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.ocm.converters.DefaultDoubleConverter;
import com.github.ocm.converters.DefaultFloatConverter;
import com.github.ocm.converters.ICSVTypeConverter;
import com.github.ocm.converters.MarkerConverter;
import com.github.ocm.mapping.OCMEngine;

/**
 * CSV Field annotation. Each field is represented as column in CSV file. If
 * field needs a convert, one must provide it as {@link #converter()} field.
 *
 * @author Sebastian Laskawiec
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {

    /**
     * Converter used for text to target object convention. There are several
     * building converters, for example: {@link DefaultFloatConverter} or
     * {@link DefaultDoubleConverter}. There is also one converter with special
     * type - {@link MarkerConverter}. {@link OCMEngine} ignores it during
     * processing and tries to figure proper converter up.
     *
     * @return Converter for this type of field.
     */
    public Class<? extends ICSVTypeConverter<?>> converter() default MarkerConverter.class;

    /**
     * CSV column representation.
     *
     * @return Representation of CSV column.
     */
    public String csvFieldName() default "";

    /**
     * @return <code>true</code> if this is optional field.
     */
    public boolean nullable() default true;

}
