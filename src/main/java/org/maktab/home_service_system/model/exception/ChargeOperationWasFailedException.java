package org.maktab.home_service_system.model.exception;

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
