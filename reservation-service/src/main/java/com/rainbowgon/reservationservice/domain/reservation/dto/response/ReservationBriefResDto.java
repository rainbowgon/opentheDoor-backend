package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationBriefResDto {

    private Long reservationId;
    private Long reservationNumber;
    private LocalDate targetDate;
    private LocalTime targetTime;
    private Integer headcount;
    private Integer totalPrice;
    private String themeId;
    private String poster;
    private String title;
    private String venue;

}
