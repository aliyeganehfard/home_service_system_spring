package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
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
