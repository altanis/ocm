package com.github.ocm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.ocm.mapping.OCMEngine;

/**
 * CSV ignore annotation. This annotation works very similar to
 * <code>transient</code> fields. {@link OCMEngine} will ignore fields annotated
 * with this annotation.
 *
 * @author Sebastian Laskawiec
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVFieldIgnore {

}
