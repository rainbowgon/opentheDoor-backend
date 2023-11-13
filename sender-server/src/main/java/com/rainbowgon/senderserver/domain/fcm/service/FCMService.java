package com.rainbowgon.senderserver.domain.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FCMService {

    private final FCMInitializer fcmInitializer;

    public boolean sendMessage(String token, String title, String body) {
        fcmInitializer.initialize();

        Message message = Message.builder()
                .setToken(token)
                .putData("title", title)
                .putData("body", body)
                .build();
        FirebaseMessaging.getInstance().sendAsync(message);

        // 메세지가 제대로 간다면
        return true;
    }

}