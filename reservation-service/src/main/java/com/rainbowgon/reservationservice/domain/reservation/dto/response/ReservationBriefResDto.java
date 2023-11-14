package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime targetTime;
    private Integer headcount;
    private Integer totalPrice;
    private String themeId;
    private String poster;
    private String title;
    private String venue;

    public static ReservationBriefResDto from(Reservation reservation, ThemeBriefInfoInDto themeDto) {
        return ReservationBriefResDto.builder()
                .reservationId(reservation.getId())
                .reservationNumber(reservation.getReservationNumber())
                .targetDate(reservation.getTargetDate())
                .targetTime(reservation.getTargetTime())
                .headcount(reservation.getHeadcount())
                .totalPrice(reservation.getTotalPrice())
                .themeId(reservation.getThemeId())
                .poster(themeDto.getPoster())
                .title(themeDto.getTitle())
                .venue(themeDto.getVenue())
                .build();
    }
}
