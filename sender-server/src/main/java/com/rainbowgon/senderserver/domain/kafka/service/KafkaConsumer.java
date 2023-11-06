package com.rainbowgon.senderserver.domain.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.senderserver.domain.kafka.dto.in.MessageInDTO;
import com.rainbowgon.senderserver.domain.sender.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final SenderService senderService;

    @KafkaListener(topics = "notification", groupId = "rainbowgon")
    public void getMessage(String message) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        MessageInDTO messageInDTO = objectMapper.readValue(message, MessageInDTO.class);

        log.info("consumed message : {}", messageInDTO);
        senderService.sendAndInsertMessage(messageInDTO);
    }
}
