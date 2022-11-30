package org.example.ers.utilities.custom_exceptions;

public class InvalidUserFieldsException extends Exception {
    public InvalidUserFieldsException() {
        super();
    }

    public InvalidUserFieldsException(String message) {
        super(message);
    }

    public InvalidUserFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserFieldsException(Throwable cause) {
        super(cause);
    }

    protected InvalidUserFieldsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
