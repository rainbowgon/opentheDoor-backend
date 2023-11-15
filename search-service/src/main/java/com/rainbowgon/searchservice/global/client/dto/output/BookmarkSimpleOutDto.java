package com.rainbowgon.searchservice.global.client.dto.output;

import com.rainbowgon.searchservice.domain.theme.model.Theme;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookmarkSimpleOutDto {

    /* 테마 정보 */
    private String themeId; // 테마 ID
    private String title; // 테마명
    private String poster; // 포스터명
    private String venue; // 지점명
    private Double ratingScore; // 평균 별점
    private Integer reviewCount; // 리뷰 수

    public static BookmarkSimpleOutDto from(Theme theme, Integer reviewCount, Double ratingScore) {
        return BookmarkSimpleOutDto.builder()
                .themeId(theme.getThemeId())
                .title(theme.getTitle())
                .venue(theme.getVenue())
                .poster(theme.getPoster())
                .reviewCount(reviewCount)
                .ratingScore(ratingScore)
                .build();
    }

}