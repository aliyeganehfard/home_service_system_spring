package org.maktab.home_service_system.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentOperationWasFailedException extends RuntimeException{
    public PaymentOperationWasFailedException() {
    }

    public PaymentOperationWasFailedException(String message) {
        super(message);
    }

    public PaymentOperationWasFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
