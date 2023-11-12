package com.rainbowgon.senderserver.domain.sender.service;

import com.rainbowgon.senderserver.domain.fcm.service.FCMService;
import com.rainbowgon.senderserver.domain.kafka.dto.input.MessageInDto;
import com.rainbowgon.senderserver.domain.sender.entity.Notification;
import com.rainbowgon.senderserver.domain.sender.repository.SenderRDBRepository;
import com.rainbowgon.senderserver.domain.sender.repository.SenderRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SenderServiceImpl implements SenderService {

    private final FCMService fcmService;
    private final SenderRDBRepository senderRDBRepository;
    private final SenderRedisRepository senderRedisRepository;

    @Override
    @Transactional
    public void sendAndInsertMessage(MessageInDto messageInDto) {

        if (fcmService.sendMessage(messageInDto.getFcmToken(), messageInDto.getTitle(),
                                   messageInDto.getBody())) {

            senderRedisRepository.save(
                    Notification.from(senderRDBRepository.save(messageInDto.toEntity())));
        }
    }
}
