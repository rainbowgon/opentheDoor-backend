package com.rainbowgon.memberservice.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.memberservice.domain.review.entity.EscapeStatus;
import com.rainbowgon.memberservice.domain.review.entity.Review;
import com.rainbowgon.memberservice.global.client.dto.input.ThemeSimpleInDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ThemeHistoryResDto { // 내가 했던 방탈출 테마 객체

    private String themeId;
    private String poster;
    private String title;
    private Double rating;
    private EscapeStatus isEscaped; // 성공실패 여부
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate performedDate;

    public static ThemeHistoryResDto of(ThemeSimpleInDto theme, Review review) {
        return ThemeHistoryResDto.builder()
                .themeId(theme.getThemeId())
                .poster(theme.getPoster())
                .title(theme.getTitle())
                .rating(review.getRating())
                .isEscaped(review.getIsEscaped())
                .performedDate(review.getPerformedDate())
                .build();
    }
}
