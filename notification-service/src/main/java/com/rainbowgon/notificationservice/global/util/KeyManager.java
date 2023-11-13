package com.rainbowgon.notificationservice.global.util;

public class KeyManager {

    public static String makeRedisKey(String header, Long id) {
        return header + id;
    }
}
