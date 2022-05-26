package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ChargeOperationWasFailedException extends RuntimeException{
    public ChargeOperationWasFailedException() {
    }

    public ChargeOperationWasFailedException(String message) {
        super(message);
    }

    public ChargeOperationWasFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
