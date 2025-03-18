package com.example.daraja.payment.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        logger.log(Level.SEVERE, "Unhandled exception occurred", ex);

        Map<String, Object> body = new HashMap<>();
        body.put("ResultCode", "1");
        body.put("ResultDesc", "Internal server error");

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        logger.log(Level.WARNING, "Validation error", ex);

        Map<String, Object> body = new HashMap<>();
        body.put("ResultCode", "1");
        body.put("ResultDesc", "Validation error");

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
