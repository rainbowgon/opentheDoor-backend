package com.rainbowgon.memberservice.global.client.dto.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInDto {

    private Long reservationId; // 예약 ID
    private LocalDate targetDate; // 예약 날짜
    private LocalTime targetTime; // 예약 시간
    private Integer headcount; // 인원
}
