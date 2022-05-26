package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreditNotFoundException extends RuntimeException{
    public CreditNotFoundException() {
    }

    public CreditNotFoundException(String message) {
        super(message);
    }

    public CreditNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
