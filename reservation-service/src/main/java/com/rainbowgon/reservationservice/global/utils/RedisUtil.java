package com.rainbowgon.reservationservice.global.utils;

public class RedisUtil {

    // TODO 단순 String을 Hash로 저장
    public static String createWaitingId(String timeLineId, String targetDate,
                                         String targetTime) {
        return String.format("%s-%s-%s", timeLineId, targetDate, targetTime);
    }

    public static String createTimeLineId(String title, String originalUrl) {
        return title + "_" + originalUrl;
    }
}
