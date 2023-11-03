package com.rainbowgon.senderserver.domain.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.rainbowgon.senderserver.domain.sender.repository.SenderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FCMService {

    private SenderRepository senderRepository;

    public void sendMessage(String token) {
        Message message = Message.builder()
                .setToken(token)
                .putData("title", "스프링부트에서 간 제목")
                .putData("body", "스프링부트에서 간 내용")
                .build();
        FirebaseMessaging.getInstance().sendAsync(message);

    }

}