package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDetailResDto {

    private String bookerName;
    private String bookerPhoneNumber;
    private String targetDate;
    private String targetTime;
    private Integer headcount;
    private Integer totalPrice;
    private String themeId;
    private String poster;
    private String title;
    private String venue;
    private String location;
    private List<String> genre;
    private String siteTos;
    private String venueTos;

    public static ReservationDetailResDto from(Reservation reservation, ThemeBriefInfoInDto themeDto) {
        return ReservationDetailResDto.builder()
                .bookerName(reservation.getBookerName())
                .bookerPhoneNumber(reservation.getBookerPhoneNumber())
                .targetDate(reservation.getTargetDate())
                .targetTime(reservation.getTargetTime())
                .headcount(reservation.getHeadcount())
                .totalPrice(reservation.getTotalPrice())
                .themeId(reservation.getThemeId())
                .poster(themeDto.getPoster())
                .title(themeDto.getTitle())
                .venue(themeDto.getVenue())
                .location(themeDto.getLocation())
                .genre(themeDto.getGenre())
                .siteTos(themeDto.getSiteTos())
                .venueTos(themeDto.getVenueTos())
                .build();
    }
}

