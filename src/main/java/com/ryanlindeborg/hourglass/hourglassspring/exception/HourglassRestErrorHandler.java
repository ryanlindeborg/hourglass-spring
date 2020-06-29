package com.ryanlindeborg.hourglass.hourglassspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HourglassRestErrorHandler {

    @ExceptionHandler(HourglassRestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HourglassRestException handleHourglassRestException(HourglassRestException e) {
        return e;
    }
}
