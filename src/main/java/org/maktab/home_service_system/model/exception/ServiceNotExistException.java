package org.maktab.home_service_system.model.exception;

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
