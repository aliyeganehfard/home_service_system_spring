package org.maktab.home_service_system.model.exception;

public class IncorrectOrderDateException extends RuntimeException{
    public IncorrectOrderDateException() {
    }

    public IncorrectOrderDateException(String message) {
        super(message);
    }

    public IncorrectOrderDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
