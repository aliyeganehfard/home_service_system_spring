package org.maktab.home_service_system.controller.exception.handler;

import org.maktab.home_service_system.controller.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadImageSizeException.class)
    public ResponseEntity<Object> BadImageSizeHandler(Exception ex, WebRequest request){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = ExpertNotFoundException.class)
    public ResponseEntity<Object> ExpertNotFoundHandler(Exception ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("expert not found "+ex);
    }

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> CustomerNotFoundHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customer {"+  ex.getMessage() + "} not found! " );
    }

    @ExceptionHandler(value = ServiceNotExistException.class)
    public ResponseEntity<Object> ServiceNotExistHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("service {"+  ex.getMessage() + "} not found! " );
    }

    @ExceptionHandler(value = OfferNotFoundException.class)
    public ResponseEntity<Object> OfferNotFoundHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("offer {"+  ex.getMessage() + "} not found! " );
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<Object> OrderNotFoundHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order {"+  ex.getMessage() + "} not found! " );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> ExceptionHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex);
    }
}
