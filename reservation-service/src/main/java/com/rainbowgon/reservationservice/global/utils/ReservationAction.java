package com.rainbowgon.reservationservice.global.utils;

import com.rainbowgon.reservationservice.global.utils.dto.ReservingServerResponseDto;
import com.rainbowgon.reservationservice.global.utils.dto.masterkey.MasterkeyRequestDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ReservationAction {

    @Value("${spring.resevation.target-url}")
    private String reservingServerUrl;

    @Value("${spring.resevation.on-off}")
    private String onOff;

    private final RestTemplate restTemplate;

    public Boolean reserveMasterkey(MasterkeyRequestDto masterkeyRequestDto) {
        if (onOff.equalsIgnoreCase("OFF")) {
            return false;
        }

        String uri = getMasterkeyRequestUrl();
        HttpEntity<MasterkeyRequestDto> requestBody = createMasterkeyRequestBody(masterkeyRequestDto);

        ReservingServerResponseDto responseDto =
                restTemplate.postForObject(uri, requestBody, ReservingServerResponseDto.class);

        System.out.println("======================================\n\n\n");
        System.out.println(responseDto);
        System.out.println("\n\n\n======================================");

        return responseDto.getIsSucceed();
    }

    @NotNull
    private static HttpEntity<MasterkeyRequestDto> createMasterkeyRequestBody(MasterkeyRequestDto masterkeyRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(masterkeyRequestDto, headers);
    }

    private String getMasterkeyRequestUrl() {
        return reservingServerUrl + "/reserve/masterkey";
    }

}