package com.ryanlindeborg.hourglass.hourglassspring;

import org.apache.commons.validator.routines.EmailValidator;

public class HourglassUtil {
    /**
     *
     * @param entry
     * @return Boolean for whether the string is a valid email, per Apache Commons Validator
     */
    public static Boolean isValidEmail(String entry) {
        return EmailValidator.getInstance().isValid(entry);
    }
}
