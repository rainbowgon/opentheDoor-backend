package com.rainbowgon.reservationservice.global.utils;

import com.rainbowgon.reservationservice.global.error.exception.DatetimeInvalidException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class DateTimeValidator {

    public static void validateDate(String targetDate) {
        try {
            LocalDate.parse(targetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw DatetimeInvalidException.EXCEPTION;
        }
    }

    public static void validateTime(String targetTime) {
        try {
            String REGEXP_PATTERN_TIME = "\\d\\d:\\d\\d";

            if (!Pattern.matches(REGEXP_PATTERN_TIME, targetTime)) {
                throw DatetimeInvalidException.EXCEPTION;
            }

            String[] split = targetTime.split(":");
            int hours = Integer.parseInt(split[0]);
            int minutes = Integer.parseInt(split[1]);

            if (minutes < 0 || 60 <= minutes || 30 < hours) {
                throw DatetimeInvalidException.EXCEPTION;
            }

        } catch (Exception e) {
            throw DatetimeInvalidException.EXCEPTION;
        }

    }
}
