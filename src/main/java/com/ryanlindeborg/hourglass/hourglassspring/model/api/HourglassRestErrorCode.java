package com.ryanlindeborg.hourglass.hourglassspring.model.api;

import java.util.HashMap;
import java.util.Map;

public enum HourglassRestErrorCode {
    RESOURCE_NOT_FOUND(1),
    INTERNAL_SERVER_ERROR(2);

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
