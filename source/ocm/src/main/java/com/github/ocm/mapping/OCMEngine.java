package com.github.ocm.mapping;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.ocm.annotations.CSVEntity;
import com.github.ocm.exceptions.ClassValidationException;
import com.github.ocm.exceptions.ConverterException;
import com.github.ocm.exceptions.ParsingException;
import com.github.ocm.utils.AnnotationProcessor;
import com.github.ocm.utils.ClassValidatorUtils;
import com.github.ocm.utils.FileUtils;

/**
 * This class is designed to handle main Object <-> CSV mapping.
 *
 * @author Sebastian Laskawiec
 */
public enum OCMEngine {

    INSTANCE;

    private static final Logger logger = Logger.getLogger(OCMEngine.class.getCanonicalName());

    Configuration configuration;

    Queue<File> filesToBeParsed;
    MappingEngine mappingEngine;

    Map<String, Class<?>> patternMapping;

    OCMEngine() {
        patternMapping = new HashMap<String, Class<?>>();
        mappingEngine = new MappingEngine();
        filesToBeParsed = new LinkedBlockingQueue<File>();
    }

    private void addPatternMapping(Class<?> aClass) {

        CSVEntity entityAnnotation = aClass.getAnnotation(CSVEntity.class);
        String pattern = entityAnnotation.regexpFilePattern();

        if (!patternMapping.containsKey(pattern)) {
            patternMapping.put(pattern, aClass);
            logger.log(Level.INFO, ("Added mapping pattern: " + aClass.getName() + " <-> " + pattern));
        } else {
            logger.log(Level.WARNING, "Annotated pattern " + aClass.getName() + " for class " + pattern
                    + " already exist in pattern mapping");
        }
    }

    void buildFileList(String csvDirectory) throws ParsingException {
        filesToBeParsed = FileUtils.getListOfCSVFiles(filesToBeParsed, configuration.getCSVDirectory(),
                configuration.getCSVExtension());
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("List of files to be parsed: " + filesToBeParsed);
        }
    }

    void buildMapping(Set<Class<?>> classes) throws ClassValidationException {

        long timer = System.currentTimeMillis();

        mappingEngine.clearMapping();
        patternMapping.clear();

        if (classes == null || classes.size() == 0) {
            if (!configuration.getUseClassAutoDescovery()) {
                throw new ClassValidationException(
                        "Configuration.useClassAutoDiscovery is set to false, but no classes were added to configuration");
            }

            logger.log(Level.INFO, "Performing automatic class discovery");
            AnnotationProcessor ap = new AnnotationProcessor();

            for (Class<?> aClass : ap.getClasses("")) {
                configuration.getClasses().add(aClass);
            }
        }

        for (Class<?> aClass : configuration.getClasses()) {

            ClassValidatorUtils.validEntityClass(aClass);

            mappingEngine.createMapping(aClass);
            addPatternMapping(aClass);
        }

        logger.log(Level.INFO, "Configuration finnished, added " + configuration.getClasses().size()
                + " class(es), took " + (System.currentTimeMillis() - timer) + " ms");
    }

    public <T> Map<String, List<T>> getEntities(Class<T> type) throws ParsingException, ConverterException {

        if (configuration == null) {
            throw new NullPointerException("Initialize engine with init method first!");
        }

        long timer = System.currentTimeMillis();

        Map<String, List<T>> ret = new HashMap<String, List<T>>();

        for (File f : filesToBeParsed) {

            List<T> list = getEntities(type, f);
            if (list != null) {
                ret.put(f.getAbsolutePath(), list);
            }
        }

        logger.log(Level.INFO, "Parsing finnished, took " + (System.currentTimeMillis() - timer) + " ms");

        return ret;
    }

    public <T> List<T> getEntities(Class<T> type, File fileToParse) throws ParsingException, ConverterException {

        if (configuration == null) {
            throw new NullPointerException("Initialize engine with init method first!");
        }

        for (String pattern : patternMapping.keySet()) {
            if (fileToParse.getAbsolutePath().matches(pattern)) {

                logger.info("Processing file: " + fileToParse.getAbsolutePath());

                return mappingEngine.mapCSVToEntity(type, fileToParse);
            }
        }

        return null;
    }

    public void Init(Configuration configuration) throws ClassValidationException, ParsingException {

        // clean old configuration first
        clearConfiguration();

        this.configuration = configuration;
        buildMapping(configuration.getClasses());

        if (configuration.getUseAutoScanDirectory()) {
            buildFileList(configuration.getCSVDirectory());
        }
    }

    /**
     * Clears configuration, mapping rules and file list. Should be used in
     * situations when other program parts depends on proper engine
     * initialization.
     */
    public void clearConfiguration() {
        configuration = null;

        mappingEngine.clearMapping();

        filesToBeParsed.clear();
        patternMapping.clear();
    }

    public void writeCSV(Collection<?> entities, String fileName, boolean override) throws ParsingException,
            ClassValidationException, ConverterException {

        if (configuration == null) {
            throw new NullPointerException("Initialize engine with init method first!");
        }

        StringBuilder filePath = new StringBuilder();
        filePath.append(configuration.getCSVDirectory());
        if (configuration.getCSVDirectory().charAt(configuration.getCSVDirectory().length() - 1) != '/'
                && configuration.getCSVDirectory().charAt(configuration.getCSVDirectory().length() - 1) != '\\') {
            filePath.append("/");
        }
        filePath.append(fileName).append(".").append(configuration.getCSVExtension());

        File file = new File(filePath.toString());
        if (file.exists() && !override) {
            throw new ParsingException("File already exist and override is set to false");
        }

        mappingEngine.mapEntityToCSV(entities, file);

    }
}
