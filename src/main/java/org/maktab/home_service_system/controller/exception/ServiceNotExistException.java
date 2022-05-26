package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceNotExistException extends RuntimeException{
    public ServiceNotExistException() {
    }

    public ServiceNotExistException(String message) {
        super(message);
    }

    public ServiceNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
