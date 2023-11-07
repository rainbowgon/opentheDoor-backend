package com.rainbowgon.memberservice.global.util;

import java.util.Random;

public class MessageFactory {

    /**
     * 인증번호 생성, 6자리 숫자
     */
    public static String generateAuthenticationNumber() {

        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(Integer.toString(rand.nextInt(10)));
        }

        return sb.toString();
    }

    /**
     * 발송 메시지 내용 생성
     */
    public static String generateMessageText(String authenticationNumber) {
        return new StringBuilder("[오픈더도어] 인증번호 [ ")
                .append(authenticationNumber)
                .append(" ]를 입력해 주세요.\n")
                .toString();
    }
}
