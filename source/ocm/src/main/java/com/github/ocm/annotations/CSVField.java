/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.ocm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.ocm.converters.ICSVTypeConverter;
import com.github.ocm.converters.MarkerConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {

    public Class<? extends ICSVTypeConverter<?>> converter() default MarkerConverter.class;

    public String csvFieldName() default "";

    public boolean nullable() default true;

}
