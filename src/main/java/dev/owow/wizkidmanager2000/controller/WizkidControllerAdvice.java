package dev.owow.wizkidmanager2000.controller;

import dev.owow.wizkidmanager2000.exception.WizkidManagerException;
import dev.owow.wizkidmanager2000.exception.WizkidNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WizkidControllerAdvice {

    @ResponseBody
    @ExceptionHandler(WizkidNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String wizkidNotFoundExceptionHandler(WizkidNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WizkidManagerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String wizkidManagerExceptionHandler(WizkidManagerException exception) {
        return exception.getMessage();
    }
}
