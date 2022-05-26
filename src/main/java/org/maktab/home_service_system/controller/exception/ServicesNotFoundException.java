package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServicesNotFoundException extends RuntimeException {
    public ServicesNotFoundException() {
    }

    public ServicesNotFoundException(String message) {
        super(message);
    }

    public ServicesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
