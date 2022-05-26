package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExpertNotFoundException extends RuntimeException{
    public ExpertNotFoundException() {
    }

    public ExpertNotFoundException(String message) {
        super(message);
    }

    public ExpertNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
