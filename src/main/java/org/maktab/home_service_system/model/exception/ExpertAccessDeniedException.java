package org.maktab.home_service_system.model.exception;

public class ExpertAccessDeniedException extends RuntimeException{
    public ExpertAccessDeniedException() {
    }

    public ExpertAccessDeniedException(String message) {
        super(message);
    }

    public ExpertAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
