package com.github.ocm.utils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.ocm.exceptions.ClassValidationException;

/**
 * Classpath searching and class validation utility class. All methods placed
 * here should be public and static. No instantiation allowed or inheritance.
 *
 * This class is tightly coupled with {@link ClassValidatorUtils}.
 *
 * @author Sebastian Laskawiec
 */
public final class AnnotationProcessor {

    static final Logger logger = Logger.getLogger(AnnotationProcessor.class.getCanonicalName());

    /**
     * Helper recurrent method for finding classes in classpath.
     */
    private List<Class<?>> findClasses(File directory, String packageName) {

        String recursiveSearchPathSeparator = null;
        if (packageName == null || "".equals(packageName)) {
            recursiveSearchPathSeparator = "";
        } else {
            recursiveSearchPathSeparator = packageName + ".";
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, recursiveSearchPathSeparator + file.getName()));
            } else if (file.getName().endsWith(".class")) {

                Class<?> currentClass;
                try {
                    currentClass = Class.forName(recursiveSearchPathSeparator
                            + file.getName().substring(0, file.getName().length() - 6));
                    if (ClassValidatorUtils.containsEntityAnnotation(currentClass)) {
                        classes.add(currentClass);
                    }
                } catch (ClassNotFoundException e) {
                    logger.log(Level.WARNING, "Unable to find class: "
                            + (recursiveSearchPathSeparator + file.getName().substring(0, file.getName().length() - 6)));
                } catch (NoClassDefFoundError e) {
                    logger.log(Level.WARNING, "Unable to find class: "
                            + (recursiveSearchPathSeparator + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }

    /**
     * Get all classes starting with package name. All errors found during
     * classpath search are logged in with WARN level.
     *
     * @param packageName
     *            Starting package name, for example java.sql or empty string
     *            for scanning whole classpath.
     * @return List of classes in classpath.
     * @throws ClassValidationException
     *             If a critical error occurs during searching.
     */
    public List<Class<?>> getClasses(String packageName) throws ClassValidationException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList<File>();
            while (resources.hasMoreElements()) {
                URI resource = resources.nextElement().toURI();
                dirs.add(new File(resource));
            }
            List<Class<?>> classes = new ArrayList<Class<?>>();
            for (File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }

            return classes;
        } catch (Exception e) {
            throw new ClassValidationException(e);
        }
    }

}
