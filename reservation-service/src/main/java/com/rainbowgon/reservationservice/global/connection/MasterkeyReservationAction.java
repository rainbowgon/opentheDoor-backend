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

    /**
     * @return 실패인 경우, false 반환
     * @return 성공인 경우, true 반환
     */
    @Override
    public Boolean reserve(ReservingServerRequestDto reservingServerRequestDto) {
        /*
          config 파일에서 `on-off` 값이 `ON`인 경우에 실제 예약 동작함
          그 외는 실제 동작을 하지 않음
          `off-return-value`가 `SUCCESS`인 경우에 성공 테스트
          `SUCCESS`가 아닌 그 외인 경우 실패 테스트
         */
        if (!config.getOnOff().equalsIgnoreCase("ON")) {
            return config.getOffReturnValue().equalsIgnoreCase("SUCCESS");
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
