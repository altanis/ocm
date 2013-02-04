package com.github.ocm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.github.ocm.annotations.CSVEntity;
import com.github.ocm.annotations.CSVFieldIgnore;
import com.github.ocm.exceptions.ClassValidationException;

/**
 * Class validation utility class. All methods placed here should be public and
 * static. No instantiation allowed or inheritance.
 *
 * @author Sebastian Laskawiec
 */
public final class ClassValidatorUtils {

    /**
     * No instantiation allowed.
     */
    private ClassValidatorUtils() {
        throw new AssertionError("no instanciation allowed");
    }

    /**
     * Returns true if class is annotated using {@link CSVEntity}.
     *
     * @param aClass
     *            Class to be examined.
     * @return True if class is annotated using {@link CSVEntity}.
     */
    public static boolean containsEntityAnnotation(Class<?> aClass) {
        return (aClass.isAnnotationPresent(CSVEntity.class));
    }

    /**
     * Gets getter name for given field. Note that class must be designed using
     * JavaBean convention.
     *
     * For example: Class field: String example; Returned getter: getExample
     *
     * @param fieldName
     *            Field base name for getter name construction.
     * @return JavaBean getter name.
     */
    public static String getGetterName(String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        return sb.toString();
    }

    /**
     * Gets setter name for given field. Note that class must be designed using
     * JavaBean convention.
     *
     * For example: Class field: String example; Returned getter: setExample
     *
     * @param fieldName
     *            Field base name for setter name construction.
     * @return JavaBean setter name.
     */
    public static String getSetterName(String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        return sb.toString();
    }

    /**
     * Validates class. Validation criterias: <li>Class must be annotated using
     * {@link CSVEntity} annotation</li> <li>Class must contain at least one
     * field</li> <li>Class must contain valid JavaBean getter/setter methods</li>
     *
     * @param aClass
     *            Class to be validated.
     * @throws ClassValidationException
     *             Thrown if one of above if violated.
     */
    public static void validEntityClass(Class<?> aClass) throws ClassValidationException {
        if (!containsEntityAnnotation(aClass)) {
            throw new ClassValidationException("Class " + aClass.getName() + " is not annotated with "
                    + CSVEntity.class.getName() + " annotation");
        }

        if (aClass.getDeclaredFields().length == 0) {
            throw new ClassValidationException("Class " + aClass.getName() + " is annotated with "
                    + CSVEntity.class.getName() + ", but does not contain any fields");
        }

        for (Field field : aClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(CSVFieldIgnore.class)) {
                String getter = getGetterName(field.getName());
                String setter = getSetterName(field.getName());

                try {
                    Method get = aClass.getMethod(getter, (Class<?>[]) null);
                    if (get.getReturnType() != field.getType()) {
                        throw new ClassValidationException("Found getter method for field: " + field.getName()
                                + " but expected return type is: " + field.getType().getName() + " but got: "
                                + get.getReturnType().getName());
                    }
                } catch (SecurityException e) {
                    throw new ClassValidationException("Getter method: " + getter + " seems not accessible", e);
                } catch (NoSuchMethodException e) {
                    throw new ClassValidationException("Could not find getter method for field: " + field.getName()
                            + ". Expecting method name: " + getter, e);
                }

                try {
                    aClass.getMethod(setter, field.getType());
                } catch (SecurityException e) {
                    throw new ClassValidationException("Setter method: " + setter + " seems not accessible", e);
                } catch (NoSuchMethodException e) {
                    throw new ClassValidationException("Could not find setter method for field: " + field.getName()
                            + ". Expecting method name: " + setter, e);
                }
            }
        }
    }

}
