package com.ryanlindeborg.hourglass.hourglassspring;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public static Date getDateMinutesAgo(int minutes) {
        Date today = new Date();
        return getDateMinutesAgo(today, minutes);
    }

    public static Date getDateMinutesAgo(Date date, int minutes) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes * -1);
        return cal.getTime();
    }

    public static Date getDateHoursAgo(int hours) {
        Date today = new Date();
        return getDateHoursAgo(today, hours);
    }

    public static Date getDateHoursAgo(Date date, int hours) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours * -1);
        return cal.getTime();
    }
}
