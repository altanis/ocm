package com.github.ocm.exceptions;

import com.github.ocm.annotations.CSVFieldIgnore;

/**
 * This exception is thrown by OCM when there is something wrong with entity
 * class. For example such class may not have getter or setter method for fields
 * (not marked by {@link CSVFieldIgnore} annotation). It is also possible, that
 * class is not accessible through reflections for some reason.
 *
 * @author Sebastian Laskawiec
 */
public class ClassValidationException extends OCMException {

    private static final long serialVersionUID = 7591809748729861773L;

    /**
     * @see Exception#Exception()
     */
    public ClassValidationException() {
        super();
    }

    /**
     * @see Exception#Exception(String)
     */
    public ClassValidationException(String arg0) {
        super(arg0);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public ClassValidationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @see Exception#Exception(Throwable)
     */
    public ClassValidationException(Throwable arg0) {
        super(arg0);
    }

}
