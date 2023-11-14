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
}
