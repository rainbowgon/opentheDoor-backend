package com.rainbowgon.senderserver.domain.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = "spring-test", groupId = "rainbowgon")
    public void consumeMessage(String message) throws IOException {
        log.info("Consumed message : {}", message);
    }
}
