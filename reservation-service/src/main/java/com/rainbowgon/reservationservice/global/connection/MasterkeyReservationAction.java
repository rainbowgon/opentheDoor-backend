package com.rainbowgon.reservationservice.global.connection;

import com.rainbowgon.reservationservice.global.config.ConnectionConfig;
import com.rainbowgon.reservationservice.global.connection.dto.ReservingServerRequestDto;
import com.rainbowgon.reservationservice.global.connection.dto.ReservingServerResponseDto;
import com.rainbowgon.reservationservice.global.utils.ConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MasterkeyReservationAction implements ReservationAction {

    private final ConnectionConfig config;
    private final RestTemplate restTemplate;

    @Override
    public Boolean reserve(ReservingServerRequestDto reservingServerRequestDto) {
        if (config.getOnOff().equalsIgnoreCase("OFF")) {
            return false;
        }

        String uri = getRequestUrl();
        HttpEntity<ReservingServerRequestDto> requestBody =
                ConnectionUtil.createRequestBody(reservingServerRequestDto);

        ReservingServerResponseDto responseDto =
                restTemplate.postForObject(uri, requestBody, ReservingServerResponseDto.class);

        return responseDto != null ? responseDto.getIsSucceed() : false;
    }

    public String getRequestUrl() {
        return config.getReservingServerUrl() + "/reserve/masterkey";
    }
}
