package com.rainbowgon.member.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.member.domain.bookmark.entity.EscapeStatus;
import com.rainbowgon.member.domain.review.entity.Review;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyReviewDetailResDto { // 내가 작성한 리뷰 객체 (테마 상세페이지 용)

    private Long reviewId;
    private Double rating;
    private EscapeStatus isEscaped;
    private Integer myLevel;
    private Integer hintCount;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate performedDate;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime performedTime;
    private Integer performedHeadcount;

    public static MyReviewDetailResDto from(Review review) {
        return MyReviewDetailResDto.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .isEscaped(review.getIsEscaped())
                .myLevel(review.getMyLevel())
                .hintCount(review.getHintCount())
                .content(review.getContent())
                .performedDate(review.getPerformedDate())
                .performedTime(review.getPerformedTime())
                .performedHeadcount(review.getPerformedHeadcount())
                .build();
    }
}
