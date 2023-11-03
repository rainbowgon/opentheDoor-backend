package com.rainbowgon.notificationservice.domain.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowgon.notificationservice.domain.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC = "spring-test";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Notification notification) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("Produce message : {}", notification);
//        this.kafkaTemplate.send(TOPIC, notification);


        kafkaTemplate.send(TOPIC, jsonString);
    }
}