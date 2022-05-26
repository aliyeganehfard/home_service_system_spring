package org.maktab.home_service_system.controller.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateOrderExpertException extends RuntimeException{
    public DuplicateOrderExpertException() {
    }

    public DuplicateOrderExpertException(String message) {
        super(message);
    }

    public DuplicateOrderExpertException(String message, Throwable cause) {
        super(message, cause);
    }
}
