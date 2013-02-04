package com.github.ocm.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.Queue;

import com.github.ocm.exceptions.ParsingException;

/**
 * File utility class. All methods placed here should be public and static. No
 * instantiation allowed or inheritance.
 *
 * @author Sebastian Laskawiec
 */
public final class FileUtils {

    /**
     * No instanciation allowed.
     */
    private FileUtils() {
        throw new AssertionError("no instanciation allowed");
    }

    /**
     * Gets list of files from given directory. {@link FileFilter} accepts only
     * files with specified extension (both lower and uppercased). Note this
     * method is not recurrent.
     *
     * @param listOfFiles
     *            Queue of which files will be added.
     * @param topDirectory
     * @param extension
     *            Extension, for example csv or CSV.
     * @return {@link Queue} of files from specified directory.
     * @throws ParsingException
     *             Thrown if directory does not exist or if specified path is
     *             not a directory.
     */
    public static Queue<File> getListOfCSVFiles(Queue<File> files, String topDirectory, final String extension)
            throws ParsingException {

        File dir = new File(topDirectory);
        if (!dir.exists()) {
            throw new ParsingException("CSV directory could not be found");
        }

        if (!dir.isDirectory()) {
            throw new ParsingException("CSV directory is not a directory");
        }

        File[] list = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return (pathname.getName().endsWith("." + extension) || pathname.getName().endsWith(
                        "." + extension.toUpperCase()));
            }
        });

        Collections.addAll(files, list);

        return files;
    }

}
