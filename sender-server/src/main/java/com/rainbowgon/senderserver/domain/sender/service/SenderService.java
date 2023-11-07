package com.rainbowgon.senderserver.domain.sender.service;

import com.rainbowgon.senderserver.domain.kafka.dto.input.MessageInDTO;

public interface SenderService {

    void sendAndInsertMessage(MessageInDTO messageInDTO);
}
