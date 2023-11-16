package com.rainbowgon.reservationservice.global.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class RedisUtil {

    // TODO 단순 String을 Hash로 저장
    public static String createWaitingId(String title, LocalDate targetDate,
                                         LocalTime targetTime) {
        String timeLineId = createTimeLineId(title);
        return String.format("%s-%s-%s", timeLineId, targetDate.toString(), targetTime.toString());
    }

    public static String createTimeLineId(String title) {
        return title;
    }
}
