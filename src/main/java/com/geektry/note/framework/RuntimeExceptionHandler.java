package com.geektry.note.framework;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Chaohang Fu
 */
@RestControllerAdvice
public class RuntimeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    ResponseEntity<RuntimeExceptionEntity> handleRuntimeException(ServiceRuntimeException exception) {
        return new ResponseEntity<>(new RuntimeExceptionEntity() {{
            setMessage(exception.getMessage());
        }}, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
