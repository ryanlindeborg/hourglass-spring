package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import java.util.HashMap;
import java.util.Map;

public enum HourglassRestErrorCode {
    //TODO: These error codes should not be the same as error codes - should be specific to application and help developer who is connecting
    RESOURCE_NOT_FOUND(404),
    UNPROCESSABLE_ENTITY(422),
    INTERNAL_SERVER_ERROR(500);

    private Integer errorCode;
    private static final Map<Integer, HourglassRestErrorCode> errorCodeMap = new HashMap<>();

    HourglassRestErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    static {
        for (HourglassRestErrorCode errorCodeValue : values()) {
            errorCodeMap.put(errorCodeValue.errorCode, errorCodeValue);
        }
    }

    public static HourglassRestErrorCode valueOfErrorCode(Integer errorCode) {
        return errorCodeMap.get(errorCode);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
