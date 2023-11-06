package com.rainbowgon.notificationservice.domain.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.notificationservice.domain.kafka.dto.out.MessageOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC = "notification";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(MessageOutDto messageOutDto) {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(messageOutDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("produce message : {}", jsonString);
        kafkaTemplate.send(TOPIC, jsonString);
    }
}