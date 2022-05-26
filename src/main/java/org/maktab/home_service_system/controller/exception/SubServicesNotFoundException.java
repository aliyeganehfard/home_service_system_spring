package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubServicesNotFoundException extends RuntimeException{
    public SubServicesNotFoundException() {
    }

    public SubServicesNotFoundException(String message) {
        super(message);
    }

    public SubServicesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
