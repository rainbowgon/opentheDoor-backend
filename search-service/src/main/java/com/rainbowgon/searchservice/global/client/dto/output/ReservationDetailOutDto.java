package com.rainbowgon.searchservice.global.client.dto.output;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import com.rainbowgon.searchservice.domain.theme.model.entry.PriceEntry;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationDetailOutDto {

    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private String location; // 지점 위치
    private String venueTos; // 예약 사항
    private String[] genre; // 장르
    private List<PriceEntry> priceList; // 가격


    public static ReservationDetailOutDto from(Theme theme) {
        return ReservationDetailOutDto.builder()
                .poster(theme.getPoster())
                .title(theme.getTitle())
                .venue(theme.getVenue())
                .location(theme.getLocation())
                .venueTos(theme.getVenueToS())
                .genre(theme.getGenre())
                .priceList(theme.getPriceList())
                .build();
    }
}
