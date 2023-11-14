package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationSuccessResDto {

    private Long reservationId;
    private Long reservationNumber;
}
