package org.example.ers.utilities.custom_exceptions;

public class InvalidTicketRequestException extends Exception {
    public InvalidTicketRequestException() {
        super();
    }

    public InvalidTicketRequestException(String message) {
        super(message);
    }

    public InvalidTicketRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTicketRequestException(Throwable cause) {
        super(cause);
    }

    protected InvalidTicketRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
