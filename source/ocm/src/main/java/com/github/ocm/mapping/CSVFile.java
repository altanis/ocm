/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.ocm.mapping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.github.ocm.exceptions.ParsingException;

/**
 * Class designed for File manipulation.
 *
 * @author Sebastian Laskawiec
 */
public class CSVFile {

    AccessType accessType = AccessType.READ;

    String delimiter = ";";

    File file;

    String[] header;
    boolean isFirstLine;

    String newLineSign = "\n";

    BufferedReader reader;

    BufferedWriter writer;

    private CSVFile(File file, String delimiter, String lineEnd, AccessType type) {
        this.delimiter = delimiter;
        this.newLineSign = lineEnd;
        this.file = file;
        accessType = type;
    }

    public static CSVFile openFileForReading(File file, String delimiter, String lineEnd) {
        CSVFile ret = new CSVFile(file, delimiter, lineEnd, AccessType.READ);
        return ret;
    }

    public static CSVFile openFileForWriting(File file, String delimiter, String lineEnd) {
        CSVFile ret = new CSVFile(file, delimiter, lineEnd, AccessType.WRITE);
        return ret;
    }

    public void closeFile() throws ParsingException {
        switch (accessType) {
            case READ: {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new ParsingException("Could not close FileReader, something is blocking stream", e);
                } finally {
                    // what ever, system will finally close this
                    reader = null;
                }
                break;
            }

            case WRITE: {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    throw new ParsingException("Could not close FileWriter, something is blocking stream", e);
                } finally {
                    writer = null;
                }
                break;
            }
        }// end switch
    }

    public boolean isFileOpened() {
        switch (accessType) {
            case READ: {
                return reader != null;
            }

            case WRITE: {
                return writer != null;
            }

            default: {
                return false;
            }
        }// end switch
    }

    public void openFile() throws ParsingException {
        switch (accessType) {
            case READ: {
                try {
                    reader = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    throw new ParsingException("File " + file + " does not exist", e);
                }
                isFirstLine = true;
                break;
            }

            case WRITE: {
                try {
                    writer = new BufferedWriter(new FileWriter(file));
                } catch (IOException e) {
                    throw new ParsingException("Could not create writer for file: " + file);
                }
                break;
            }
        }// end switch
    }

    public String[] readNewLine() throws ParsingException {

        if (accessType != AccessType.READ) {
            throw new ParsingException("CSV access type must be set to reading, use OpenFileForReading!");
        }

        String[] ret = null;
        try {
            String line = reader.readLine();
            ret = null;

            if (line != null) {
                ret = line.split(delimiter);
            }

            if (isFirstLine) {
                header = ret;
                isFirstLine = false;
            }
        } catch (IOException e) {
            throw new ParsingException("Could not parse line of CSV, something is wrone with the file: " + file, e);
        }

        return ret;
    }

    public void writeLine(String... strings) throws ParsingException {
        if (accessType != AccessType.WRITE) {
            throw new ParsingException("CSV access type must be set to writing, use OpenFileForWriting!");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; ++i) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                sb.append(delimiter);
            }
        }
        sb.append(newLineSign);
        try {
            writer.write(sb.toString());
        } catch (IOException e) {
            throw new ParsingException("Could not write to file " + file, e);
        }
    }

}
