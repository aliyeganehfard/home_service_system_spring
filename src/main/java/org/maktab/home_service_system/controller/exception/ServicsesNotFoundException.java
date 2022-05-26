package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServicsesNotFoundException extends RuntimeException{
    public ServicsesNotFoundException() {
    }

    public ServicsesNotFoundException(String message) {
        super(message);
    }

    public ServicsesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
