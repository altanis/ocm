package com.github.ocm.exceptions;

/**
 * This exception occurs when some parsing error occurs.
 *
 * @author Sebastian Laskawiec
 */
public class ParsingException extends OCMException {

    private static final long serialVersionUID = 4580651469771063022L;

    public ParsingException() {

    }

    public ParsingException(String arg0) {
        super(arg0);
    }

    public ParsingException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ParsingException(Throwable arg0) {
        super(arg0);
    }

}
