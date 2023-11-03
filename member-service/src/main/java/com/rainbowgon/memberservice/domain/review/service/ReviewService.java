package com.rainbowgon.memberservice.domain.review.service;


import com.rainbowgon.memberservice.domain.review.dto.request.ReviewCreateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.request.ReviewUpdateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.response.MyReviewDetailResDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ReviewDetailResDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ThemeHistoryResDto;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    MyReviewDetailResDto createReview(UUID memberId, ReviewCreateReqDto reviewCreateReqDto);

    MyReviewDetailResDto updateReview(UUID memberId, ReviewUpdateReqDto reviewUpdateReqDto);

    ReviewDetailResDto selectThemeReview(Long themeId);

    List<ReviewDetailResDto> selectThemeReviewList(Long themeId);

    MyReviewDetailResDto selectMyThemeReview(UUID memberId, Long themeId);

    void deleteReview(UUID memberId, Long reviewId);

    List<MyReviewDetailResDto> selectMyReviewList(UUID memberId);

    List<ThemeHistoryResDto> selectThemeHistory(Long profileId);

}
