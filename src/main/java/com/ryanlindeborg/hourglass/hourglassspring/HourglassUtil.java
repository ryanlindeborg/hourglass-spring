package com.ryanlindeborg.hourglass.hourglassspring;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public class HourglassUtil {
    /**
     *
     * @param entry
     * @return Boolean for whether the string is a valid email, per Apache Commons Validator
     */
    public static Boolean isValidEmail(String entry) {
        return EmailValidator.getInstance().isValid(entry);
    }

    public static Boolean isEmptyOrNull(List list) {
        return (list == null || list.isEmpty());
    }
}
