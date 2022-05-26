package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadImageSizeException extends RuntimeException{
    public BadImageSizeException() {
    }

    public BadImageSizeException(String message) {
        super(message);
    }

    public BadImageSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
