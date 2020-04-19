package com.ryanlindeborg.hourglass.hourglassspring.exception;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.HourglassRestErrorCode;

public class HourglassRestException extends Exception {
    private HourglassRestErrorCode errorCode;
    private String message;

    public HourglassRestException(String message, HourglassRestErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public HourglassRestException(String message, Throwable cause, HourglassRestErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}
