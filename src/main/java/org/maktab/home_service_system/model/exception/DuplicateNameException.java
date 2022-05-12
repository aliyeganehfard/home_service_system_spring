package org.maktab.home_service_system.model.exception;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException() {
    }

    public DuplicateNameException(String message) {
        super(message);
    }

    public DuplicateNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
