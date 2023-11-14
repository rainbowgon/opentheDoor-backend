package com.rainbowgon.memberservice.domain.bookmark.dto.response;

import com.rainbowgon.memberservice.global.client.dto.input.ThemeSimpleInDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookmarkSimpleResDto { // 홈 화면에서 보여지는 북마크 리스트 객체

    private String themeId; // 테마 ID
    private String poster; // 테마 포스터
    private String title; // 테마명
    private String venue; // 지점명
    private Double ratingScore; // 평균 별점
    private Integer reviewCount; // 리뷰 수(별점 수)

    public static BookmarkSimpleResDto from(ThemeSimpleInDto theme) {
        return BookmarkSimpleResDto.builder()
                .themeId(theme.getThemeId())
                .poster(theme.getPoster())
                .title(theme.getTitle())
                .venue(theme.getVenue())
                .ratingScore(theme.getRatingScore())
                .reviewCount(theme.getReviewCount())
                .build();
    }
}
