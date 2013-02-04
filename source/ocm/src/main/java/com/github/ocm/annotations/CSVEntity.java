package com.github.ocm.annotations;

import java.io.BufferedReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSV Entity annotation. Each class used as entity in mapping must be annotated
 * by this Annotation. Annotated class must also meet this requironments:
 * <p>
 * <li>Must have default constructor</li>
 * <li>For each field annotated with {@link CSVField} or not annotated with
 * {@link CSVFieldIgnore} there must be JavaBean convention setter and getter
 * methods</li>
 * <li>Each setter/getter must be accessible from outside of the package</li>
 * </p>
 *
 * @author Sebastian Laskawiec
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVEntity {

    /**
     * Delimiter used in CSV files. Default delimiter is ";".
     */
    String delimiter() default ";";

    /**
     * New line sign used in CSV writing. CSV reading is using build-in
     * {@link BufferedReader}, so it does not matter what is the sign.
     */
    String newLineString() default "\n";

    /**
     * Regexp pattern used for automatic file collection. Regexps from different
     * classes should not overlap, in such cases only one of the classes will be
     * discovered by automatic file discovery engine.
     */
    String regexpFilePattern() default ".*";

}
