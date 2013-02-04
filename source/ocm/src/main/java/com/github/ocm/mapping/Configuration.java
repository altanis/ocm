package com.github.ocm.mapping;

import java.util.HashSet;
import java.util.Set;

import com.github.ocm.annotations.CSVEntity;

/**
 * OCM needs configuration to run properly. This includes:
 * <p>
 * <li>CSV extension (default is .csv)</li>
 * <li>CSV files directory used for parsing</li>
 * <li>
 * Annotated class autodiscovery</li>
 * <li>CSV files autodiscovery</li>
 * </p>
 *
 * Use Configuration builder to create proper configuration by calling
 * {@link Configuration.Builder#build()} method.
 *
 * @author Sebastian Laskawiec
 */
public final class Configuration {

    /**
     * Configuration Builder.
     *
     * @author Sebastian Laskawiec
     */
    public static class Builder {
        private boolean useAutoScanDirectory = true;
        private boolean useClassAutoDiscovery = true;

        private String csvExtension = "csv";
        private String csvDirectoryPath;

        private Set<Class<?>> classes;

        /**
         * Creates new Configuration Builder. After settings all properties,
         * create new configuration with {@link #build()} method.
         *
         * @param csvDirectory
         *            Directory with CSV files.
         */
        public Builder(String csvDirectory) {
            csvDirectoryPath = csvDirectory;
            classes = new HashSet<Class<?>>();
        }

        /**
         * Adds entity classes. Note that all classes must be annotated with
         * {@link CSVEntity}.
         *
         * @param aClass
         *            Class annotated with {@link CSVEntity}.
         * @return This builder.
         */
        public Builder addEntityClasses(Class<?> aClass) {
            classes.add(aClass);
            return this;
        }

        /**
         * Creates new immutable configuration based on this builder.
         *
         * @return Built coniguration.
         */
        public Configuration build() {
            return new Configuration(this);
        }

        /**
         * Enables or disables Auto Scan for annotated classes feature. If this
         * feature is set to <code>true</code> - {@link OCMEngine} will scan
         * classpath and will add all annotated classes for scanning.
         *
         * @param enabled
         *            <code>True</code> if this feature is enabled.
         * @return This builder.
         */
        public Builder setClassAutoDiscovery(boolean enabled) {
            useClassAutoDiscovery = enabled;
            return this;
        }

        /**
         * Enables or disables Auto Scan directory feature. If this feature is
         * set to <code>true</code> - {@link OCMEngine} will scan CSV directory
         * and will add all files to parsing queue.
         *
         * @param enabled
         *            <code>True</code> if this feature is enabled.
         * @return This builder.
         */
        public Builder setUseAutoScanDirectory(boolean enabled) {
            useAutoScanDirectory = enabled;
            return this;
        }

        /**
         * Sets CSV file extension. Typically this is <code>.csv</code>.
         *
         * @param extension
         *            Extension to be set.
         * @return This builder.
         */
        public Builder setCSVExtension(String extension) {
            csvExtension = extension;
            return this;
        }
    }

    final boolean useAutoScanDirectory;
    final boolean useClassAutoDiscovery;

    final String csvExtension;
    final String csvDirectoryPath;

    final Set<Class<?>> classes;

    Configuration(Configuration.Builder builder) {
        this.csvDirectoryPath = builder.csvDirectoryPath;
        this.useClassAutoDiscovery = builder.useClassAutoDiscovery;
        this.useAutoScanDirectory = builder.useAutoScanDirectory;
        this.csvExtension = builder.csvExtension;

        classes = new HashSet<Class<?>>();
        for (Class<?> aClass : builder.classes) {
            classes.add(aClass);
        }
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    public String getCSVDirectory() {
        return csvDirectoryPath;
    }

    public boolean getUseAutoScanDirectory() {
        return useAutoScanDirectory;
    }

    public boolean getUseClassAutoDescovery() {
        return useClassAutoDiscovery;
    }

    public String getCSVExtension() {
        return csvExtension;
    }

}
