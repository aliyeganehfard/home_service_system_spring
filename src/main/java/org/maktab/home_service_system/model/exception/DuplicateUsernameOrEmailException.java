package org.maktab.home_service_system.model.exception;

public class DuplicateUsernameOrEmailException extends RuntimeException{
    public DuplicateUsernameOrEmailException() {
    }

    public DuplicateUsernameOrEmailException(String message) {
        super(message);
    }

    public DuplicateUsernameOrEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
