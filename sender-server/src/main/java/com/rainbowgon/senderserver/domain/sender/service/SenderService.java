package com.rainbowgon.senderserver.domain.sender.service;

import com.rainbowgon.senderserver.domain.kafka.dto.in.MessageInDTO;

public interface SenderService {

    void sendAndInsertMessage(MessageInDTO messageInDTO);
}
