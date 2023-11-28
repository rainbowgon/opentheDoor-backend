package com.rainbowgon.reservationservice.global.connection.dto;

import com.rainbowgon.reservationservice.domain.reservation.dto.request.ReservationReqDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeOriginalInfoInDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservingServerRequestDto {

    private String targetUrl;
    private String themeTitle;
    private String targetDate;
    private String targetTime;
    private String bookerName;
    private String bookerPhoneNumber;

    public static ReservingServerRequestDto from(ThemeOriginalInfoInDto originalInfoInDto,
                                                 ReservationReqDto reservationReqDto) {
        return ReservingServerRequestDto.builder()
                .targetUrl(originalInfoInDto.getOriginalUrl())
                .themeTitle(originalInfoInDto.getTitle())
                .targetDate(reservationReqDto.getTargetDate().toString())
                .targetTime(reservationReqDto.getTargetTime().toString())
                .bookerName(reservationReqDto.getBookerName())
                .bookerPhoneNumber(reservationReqDto.getBookerPhoneNumber())
                .build();
    }
}
