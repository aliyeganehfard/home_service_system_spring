package org.maktab.home_service_system.model.exception;

public class BadPasswordException extends RuntimeException{
    public BadPasswordException() {
    }

    public BadPasswordException(String message) {
        super(message);
    }

    public BadPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
