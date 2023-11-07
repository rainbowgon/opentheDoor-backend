package com.rainbowgon.senderserver.domain.sender.service;

import com.rainbowgon.senderserver.domain.fcm.service.FCMInitializer;
import com.rainbowgon.senderserver.domain.fcm.service.FCMService;
import com.rainbowgon.senderserver.domain.kafka.dto.in.MessageInDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SenderServiceImpl implements SenderService {

    private final FCMInitializer fcmInitializer;
    private final FCMService fcmService;

    @Override
    public void sendAndInsertMessage(MessageInDTO messageInDTO) {

        fcmInitializer.initialize();
        boolean result = fcmService.sendMessage(messageInDTO.getFcmToken(), messageInDTO.getTitle(),
                                                messageInDTO.getBody());
        if (result) {
            // 메세지가 제대로 갔다면
            // 디비에 넣어야
        }

    }
}
