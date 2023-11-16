package com.rainbowgon.reservationservice.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.reservationservice.domain.reservation.entity.Reservation;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDetailResDto {

    private String bookerName;
    private String bookerPhoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime targetTime;
    private Integer headcount;
    private Integer totalPrice;
    private String themeId;
    private String poster;
    private String title;
    private String venue;
    private String location;
    private List<String> genre;
    private String siteToS;
    private String venueToS;

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
                .themeId(themeDto.getTitle())
                .venue(themeDto.getVenue())
                .location(themeDto.getLocation())
                .genre(themeDto.getGenre())
                .siteToS(themeDto.getSiteToS())
                .venueToS(themeDto.getVenueToS())
                .build();
    }
}

