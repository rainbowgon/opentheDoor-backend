package com.rainbowgon.memberservice.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.memberservice.domain.review.entity.EscapeStatus;
import com.rainbowgon.memberservice.domain.review.entity.Review;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ThemeHistoryResDto { // 내가 했던 방탈출 테마 객체

    private Long themeId;
    private String themePoster;
    private String themeTitle;
    private Double rating;
    private EscapeStatus isEscaped; // 성공실패 여부
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate performedDate;

    public static ThemeHistoryResDto of(Long themeId, String themePoster, String themeTitle, Review review) {
        return ThemeHistoryResDto.builder()
                .themeId(themeId)
                .themePoster(themePoster)
                .themeTitle(themeTitle)
                .rating(review.getRating())
                .isEscaped(review.getIsEscaped())
                .performedDate(review.getPerformedDate())
                .build();
    }
}
