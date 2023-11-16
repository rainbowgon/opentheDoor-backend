package com.rainbowgon.reservationservice.global.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class RedisUtil {

    // TODO 단순 String을 Hash로 저장
    public static String createWaitingId(String title, String posterUrl, LocalDate targetDate,
                                         LocalTime targetTime) {
        return String.format("%s-%s-%s-%s", title, posterUrl, targetDate.toString(), targetTime.toString());
    }

    public static String createTimeLineId(String title) {
        return title;
    }
}
