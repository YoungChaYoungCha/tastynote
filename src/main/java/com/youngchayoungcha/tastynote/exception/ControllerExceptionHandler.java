package com.youngchayoungcha.tastynote.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;

// Controller 혹은 Rest Controller에서 발생한 예외를 한 곳에서 관리하고 처리할 수 있게 도와주는 어노테이션.
@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ElementNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleElementNotFoundException() {
        logger.error("handle element not found exception");

        ErrorResponse errorResponse = ErrorResponse.create()
                .message("Element Not found")
                .status(404);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException() {
        logger.error("handle constraint violation exception");

        ErrorResponse errorResponse = ErrorResponse.create()
                .message("Constraint Violdation")
                .status(409);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidParameterException() {
        logger.error("handle invaild violation exception");

        ErrorResponse errorResponse = ErrorResponse.create()
                .message("Invalid Parameter")
                .status(400);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ErrorResponse> handleIoException(){
        logger.error("File io exception has been occurred");

        ErrorResponse errorResponse = ErrorResponse.create()
                .message("File upload error")
                .status(500);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
