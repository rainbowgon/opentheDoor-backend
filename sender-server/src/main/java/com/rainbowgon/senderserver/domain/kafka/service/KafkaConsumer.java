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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "notification", groupId = "rainbowgon")
    public void getMessage(String message) throws IOException {

        log.info("consumed message : {}", message);
        MessageInDTO messageInDTO = objectMapper.readValue(message, MessageInDTO.class);
        senderService.sendAndInsertMessage(messageInDTO);
    }
}
