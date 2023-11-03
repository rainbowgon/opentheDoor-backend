package com.rainbowgon.memberservice.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.memberservice.domain.bookmark.entity.EscapeStatus;
import com.rainbowgon.memberservice.domain.review.entity.Review;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewDetailResDto { // 다른 사람이 작성한 리뷰 객체 (테마 상세페이지 용)

    private Long reviewId;
    private Long profileId; // 다른 사람의 프로필 아이디
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

    public static ReviewDetailResDto from(Review review) {
        return ReviewDetailResDto.builder()
                .reviewId(review.getId())
                .profileId(review.getProfileId())
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
