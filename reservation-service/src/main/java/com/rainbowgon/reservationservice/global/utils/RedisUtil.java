package com.rainbowgon.reservationservice.global.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class RedisUtil {

    public static String createWaitingKey(String title, String posterUrl, LocalDate targetDate,
                                          LocalTime targetTime) {
        return String.format("%s_%s_%s_%s", title, posterUrl, targetDate.toString(), targetTime.toString());
    }
}
