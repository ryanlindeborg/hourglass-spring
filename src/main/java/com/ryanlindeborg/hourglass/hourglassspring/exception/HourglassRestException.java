package com.ryanlindeborg.hourglass.hourglassspring.exception;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.HourglassRestErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourglassRestException extends RuntimeException {
    private HourglassRestErrorCode errorCode;
    private HttpStatus httpStatus;
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
}
