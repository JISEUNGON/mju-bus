package com.mjubus.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusNotFoundExcpetion.class)
    public ResponseEntity<ApiErrorResponse> handleException(BusNotFoundExcpetion ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0001","No Bus is found with ID : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusCalenderNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BusCalenderNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0002", "No Calendar is found in today" + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}