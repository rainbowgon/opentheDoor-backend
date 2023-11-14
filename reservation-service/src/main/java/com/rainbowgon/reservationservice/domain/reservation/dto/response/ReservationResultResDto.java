package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationResultResDto {

    private ReservationSuccess isSucceed;
    private Long reservationId;
    private Long reservationNumber;

    public static ReservationResultResDto fail() {
        return ReservationResultResDto.builder()
                .isSucceed(ReservationSuccess.FAIL)
                .reservationId(null)
                .reservationNumber(null)
                .build();
    }

    public static ReservationResultResDto success(Long reservationId, Long reservationNumber) {
        return ReservationResultResDto.builder()
                .isSucceed(ReservationSuccess.SUCCESS)
                .reservationId(reservationId)
                .reservationNumber(reservationNumber)
                .build();
    }
}
