package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectOrderDateException extends RuntimeException{
    public IncorrectOrderDateException() {
    }

    public IncorrectOrderDateException(String message) {
        super(message);
    }

    public IncorrectOrderDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
