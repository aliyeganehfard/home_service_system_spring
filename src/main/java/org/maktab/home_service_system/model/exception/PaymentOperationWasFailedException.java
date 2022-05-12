package org.maktab.home_service_system.model.exception;

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
