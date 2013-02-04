package com.github.ocm.exceptions;

/**
 * This exception is reserved for
 * {@link com.github.ocm.converters.ICSVTypeConverter} and its sub types. It
 * indicates that some parsing/converting exception occured.
 *
 * @author Sebastian Laskawiec
 */
public class ConverterException extends OCMException {

    private static final long serialVersionUID = -175864662483063385L;

    private boolean stopProcessing = true;

    /**
     * @see Exception#Exception()
     */
    public ConverterException() {
        super();
    }

    /**
     * @see Exception#Exception(String)
     */
    public ConverterException(String arg0) {
        super(arg0);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public ConverterException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @see Exception#Exception(Throwable)
     */
    public ConverterException(Throwable arg0) {
        super(arg0);
    }


    public boolean getStopProcessing() {
        return stopProcessing;
    }

    public void setStopProcessing(boolean stopProcessing) {
        this.stopProcessing = stopProcessing;
    }

}
