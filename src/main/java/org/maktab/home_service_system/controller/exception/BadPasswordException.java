package org.maktab.home_service_system.controller.exception;

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
