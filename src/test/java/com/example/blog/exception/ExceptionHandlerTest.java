package com.example.blog.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionHandlerTest {

    @Test
    void testHandleIllegalArgumentException_BAD_REQUEST() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request");

        ResponseEntity<ExceptionHandler.Message> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleIllegalArgumentException_NOT_FOUND() {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        IllegalArgumentException exception = new IllegalArgumentException();

        ResponseEntity<ExceptionHandler.Message> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
