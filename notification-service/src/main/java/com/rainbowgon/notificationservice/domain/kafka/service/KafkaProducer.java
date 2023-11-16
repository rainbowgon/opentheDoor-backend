package com.rainbowgon.notificationservice.domain.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.notificationservice.domain.kafka.dto.output.MessageOutDto;
import com.rainbowgon.notificationservice.global.error.exception.JsonToStringException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public cl
        ass KafkaProducer {

    private final String TOPIC = "noti";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(MessageOutDto messageOutDto) {

        String jsonString = jsonToString(messageOutDto);

        log.info("produce message : {}", jsonString);
        kafkaTemplate.send(TOPIC, jsonString);
    }

    private String jsonToString(MessageOutDto messageOutDto) {

        try {
            return objectMapper.writeValueAsString(messageOutDto);
        } catch (JsonProcessingException e) {
            throw JsonToStringException.EXCEPTION;
        }
    }
}