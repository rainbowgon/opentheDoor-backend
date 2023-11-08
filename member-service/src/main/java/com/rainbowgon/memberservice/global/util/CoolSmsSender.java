package com.rainbowgon.memberservice.global.util;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class CoolSmsSender {

    @Value("${spring.coolsms.api.key}")
    private String apiKey;

    @Value("${spring.coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${spring.coolsms.sender}")
    private String senderNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    /**
     * cool sms 를 통해 단일 메시지 발송
     */
    public String sendOne(String phoneNumber) {

        Message message = new Message();

        // 인증번호 생성
        String authenticationNumber = MessageFactory.generateAuthenticationNumber();

        // 메시지 생성
        message.setFrom(senderNumber);
        message.setTo(phoneNumber);
        message.setText(MessageFactory.generateMessageText(authenticationNumber));

        // 메시지 발송
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        log.info("[CoolSmsSender] 발송 메시지 확인" + response);

        return authenticationNumber;
    }

}
