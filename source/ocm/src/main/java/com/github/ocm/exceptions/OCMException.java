package com.github.ocm.exceptions;

public class OCMException extends Exception {

    private static final long serialVersionUID = 5101574732249178466L;

    public OCMException() {
        super();
    }

    public OCMException(String message, Throwable cause) {
        super(message, cause);
    }

    public OCMException(String message) {
        super(message);
    }

    public OCMException(Throwable cause) {
        super(cause);
    }



}
