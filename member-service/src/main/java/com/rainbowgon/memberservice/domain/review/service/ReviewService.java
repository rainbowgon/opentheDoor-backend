package com.rainbowgon.memberservice.domain.review.service;


import com.rainbowgon.memberservice.domain.review.dto.request.ReviewCreateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.request.ReviewUpdateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ReviewDetailResDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ThemeHistoryResDto;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    ReviewDetailResDto createReview(UUID memberId, ReviewCreateReqDto reviewCreateReqDto);

    ReviewDetailResDto updateReview(UUID memberId, ReviewUpdateReqDto reviewUpdateReqDto);

    List<ReviewDetailResDto> selectThemeReview(String themeId);

    List<ReviewDetailResDto> selectThemeReviewList(String themeId);

    ReviewDetailResDto selectMyThemeReview(UUID memberId, String themeId);

    void deleteReview(UUID memberId, Long reviewId);

    List<ReviewDetailResDto> selectMyReviewList(UUID memberId);

    List<ThemeHistoryResDto> selectMyThemeHistory(UUID memberId);

}
