package com.example.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.logging.Logger;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = Logger.getLogger(ControllerExceptionHandler.class.getName());

    // ERROR 400 Exemple: неправильный метод
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(HttpClientErrorException e)
    {
        logger.severe("Error 400");
        return ResponseEntity.status(e.getStatusCode()).body("Error 400\nHttp Client Error Exception\n" + e.getMessage());
    }

    // ERROR 404
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<Object> handleNoResourceFoundException(NoHandlerFoundException e)
    {
        logger.severe("Error 404");
        return ResponseEntity.status(e.getStatusCode()).body("Error 404\nNo Handler Found Exception\n" + e.getMessage());
    }

    // ERROR 405
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e)
    {
        logger.severe("Error 405");
        return ResponseEntity.status(e.getStatusCode()).body("Error 405\nHttpRequest Method Not Supported Exception\n" + e.getMessage());
    }

    // ERROR 400 -http://localhost:8080/api/v2/deleteBlog?blogId=132
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handlerRuntimeException(RuntimeException e)
    {
        logger.severe("Error 400");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error 400\nRuntime Exception\n" + e.getMessage());
    }

    // ERROR 404 - http://localhost:8080/api/v1/user?id=1312
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e)
    {
        logger.severe("Error 404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error 404\nIllegal Argument Exception\n" + e.getMessage());
    }

    // ERROR 400 - Exemple: http://localhost:8080/api/v1/user?id
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handlerRuntimeException(MissingServletRequestParameterException e)
    {
        logger.severe("Error 400: Bad Request");
        return ResponseEntity.status(e.getStatusCode()).body("Error 400\nMissing Servlet Request Parameter Exception\n" + e.getMessage());
    }

    // ERROR 404 - Exemple: http://localhost:8080/api/v1/use
    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<Object> noResourceFoundException(NoResourceFoundException e)
    {
        logger.severe("Error 404: Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error 404\nNo Resource Found Exception\n" + e.getMessage());
    }






    // Default Exception
    /* @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> Exception(Exception e)
    {
        logger.severe("Default Exception");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unknown exeption: " + e.getMessage());
    } */
}