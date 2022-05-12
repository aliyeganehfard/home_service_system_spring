package org.maktab.home_service_system.model.exception;

public class BadImageSizeException extends RuntimeException{
    public BadImageSizeException() {
    }

    public BadImageSizeException(String message) {
        super(message);
    }

    public BadImageSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
