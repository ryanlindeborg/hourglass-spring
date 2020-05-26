package com.ryanlindeborg.hourglass.hourglassspring.exception;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.HourglassRestErrorCode;

import java.util.List;

public class HourglassRestException extends RuntimeException {
    private HourglassRestErrorCode errorCode;
    private String message;
    private List<String> errors;

    public HourglassRestException(String message, HourglassRestErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public HourglassRestException(String message, Throwable cause, HourglassRestErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public HourglassRestException(String message, HourglassRestErrorCode errorCode, List<String> errors){
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }
}
