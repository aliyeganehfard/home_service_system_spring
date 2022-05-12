package org.maktab.home_service_system.model.exception;

public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException() {
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}
