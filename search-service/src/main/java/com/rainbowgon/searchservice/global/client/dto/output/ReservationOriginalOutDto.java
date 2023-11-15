package com.rainbowgon.searchservice.global.client.dto.output;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationOriginalOutDto {

    private String originalUrl; // url
    private String originalPoster; // 오리지널 포스터
    private String venue; // 지점명
    private String title; // 테마명

    public static ReservationOriginalOutDto from(Theme theme) {
        return ReservationOriginalOutDto.builder()
                .title(theme.getTitle())
                .venue(theme.getVenue())
                .originalUrl(theme.getOriginalUrl())
                .originalPoster(theme.getOriginalPoster())
                .build();
    }
}
