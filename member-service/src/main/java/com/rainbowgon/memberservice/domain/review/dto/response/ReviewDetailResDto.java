package com.rainbowgon.memberservice.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.review.entity.EscapeStatus;
import com.rainbowgon.memberservice.domain.review.entity.Review;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewDetailResDto {

    /* 리뷰 작성 정보 */
    private Long reviewId;
    private Long profileId;
    private String nickname;
    private String profileImage;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reviewCreatedDate; // 리뷰 작성일
    private Boolean isVerified; // 리뷰 인증 여부(예약 정보가 있는 리뷰면 true)

    /* 테마 후기 정보 */
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
                .rating(review.getRating())
                .isEscaped(review.getIsEscaped())
                .myLevel(review.getMyLevel())
                .hintCount(review.getHintCount())
                .content(review.getContent())
                .performedDate(review.getPerformedDate())
                .performedTime(review.getPerformedTime())
                .performedHeadcount(review.getPerformedHeadcount())
                .isVerified(review.getReservationId() != null)
                .reviewCreatedDate(review.getCreatedAt().toLocalDate())
                .build();
    }

    public static ReviewDetailResDto of(Review review, ProfileSimpleResDto profile) {
        return ReviewDetailResDto.builder()
                .reviewId(review.getId())
                .profileId(profile.getProfileId())
                .nickname(profile.getNickname())
                .profileImage(profile.getProfileImage())
                .reviewCreatedDate(review.getCreatedAt().toLocalDate())
                .isVerified(review.getReservationId() != null)
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
