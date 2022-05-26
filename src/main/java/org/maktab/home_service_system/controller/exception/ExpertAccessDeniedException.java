package org.maktab.home_service_system.controller.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExpertAccessDeniedException extends RuntimeException{
    public ExpertAccessDeniedException() {
    }

    public ExpertAccessDeniedException(String message) {
        super(message);
    }

    public ExpertAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
