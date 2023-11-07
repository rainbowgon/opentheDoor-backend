package com.rainbowgon.senderserver.domain.sender.service;

import com.rainbowgon.senderserver.domain.kafka.dto.input.MessageInDto;

public interface SenderService {

    void sendAndInsertMessage(MessageInDto messageInDto);
}
