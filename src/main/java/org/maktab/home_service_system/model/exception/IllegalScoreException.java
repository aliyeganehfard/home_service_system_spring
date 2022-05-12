package org.maktab.home_service_system.model.exception;

public class IllegalScoreException extends RuntimeException{
    public IllegalScoreException() {
    }

    public IllegalScoreException(String message) {
        super(message);
    }

    public IllegalScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
