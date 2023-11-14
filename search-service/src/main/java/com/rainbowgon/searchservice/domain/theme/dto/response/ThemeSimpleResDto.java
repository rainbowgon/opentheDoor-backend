package com.rainbowgon.searchservice.domain.theme.dto.response;


import com.rainbowgon.searchservice.domain.theme.model.Theme;
import com.rainbowgon.searchservice.domain.theme.model.entry.PriceEntry;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThemeSimpleResDto {

    private String themeId;
    private String venue; // 지점명
    private String title; // 테마명
    private String poster; // 포스터
    private Double level; // 난이도
    private Integer minHeadcount; // 최소 인원 수
    private Integer maxHeadcount; // 최대 인원 수
    private List<PriceEntry> priceList; // 가격
    private Integer timeLimit; // 소요 시간 (분 단위)
    private Double latitude; // 위도
    private Double longitude; // 경도
    private Double ratingScore; // 테마 평균 별점
    private Integer reviewCount; // 테마 리뷰 수
    private Integer bookmarkCount; // 북마크 리뷰 수

    public static ThemeSimpleResDto from(Theme theme, Integer bookmarkCount, Integer reviewCount,
                                         Double ratingScore) {
        return ThemeSimpleResDto.builder()
                .themeId(theme.getThemeId())
                .venue(theme.getVenue())
                .title(theme.getTitle())
                .poster(theme.getPoster())
                .level(theme.getLevel())
                .priceList(theme.getPriceList())
                .minHeadcount(theme.getMinHeadcount())
                .maxHeadcount(theme.getMaxHeadcount())
                .latitude(theme.getLatitude())
                .longitude(theme.getLongitude())
                .bookmarkCount(bookmarkCount)
                .reviewCount(reviewCount)
                .ratingScore(ratingScore)
                .build();
    }

}
