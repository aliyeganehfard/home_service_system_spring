package org.maktab.home_service_system.model.exception;

public class DuplicateOrderExpertException extends RuntimeException{
    public DuplicateOrderExpertException() {
    }

    public DuplicateOrderExpertException(String message) {
        super(message);
    }

    public DuplicateOrderExpertException(String message, Throwable cause) {
        super(message, cause);
    }
}
