package com.rainbowgon.memberservice.domain.review.controller;

import com.rainbowgon.memberservice.domain.review.dto.request.ReviewCreateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.request.ReviewUpdateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ReviewDetailResDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ThemeHistoryResDto;
import com.rainbowgon.memberservice.domain.review.service.ReviewService;
import com.rainbowgon.memberservice.global.response.JsonResponse;
import com.rainbowgon.memberservice.global.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     */
    @PostMapping
    public ResponseEntity<ResponseWrapper<ReviewDetailResDto>> createReview(
            @RequestHeader("memberId") String memberId,
            @RequestBody ReviewCreateReqDto reviewCreateReqDto) {

        ReviewDetailResDto myReview = reviewService.createReview(UUID.fromString(memberId), reviewCreateReqDto);

        return JsonResponse.ok("리뷰가 성공적으로 생성되었습니다.", myReview);
    }

    /**
     * 리뷰 수정
     * 수정 사항 있으면 변경된 값, 수정 사항 없어도 기존값 그대로 요청
     */
    @PatchMapping
    public ResponseEntity<ResponseWrapper<ReviewDetailResDto>> updateReview(
            @RequestHeader("memberId") String memberId,
            @RequestBody ReviewUpdateReqDto reviewUpdateReqDto) {

        ReviewDetailResDto myReview = reviewService.updateReview(UUID.fromString(memberId), reviewUpdateReqDto);

        return JsonResponse.ok("리뷰가 성공적으로 수정되었습니다.", myReview);
    }

    /**
     * 특정 테마의 리뷰 1건 조회 (비회원 테마 상세 페이지)
     * 각 테마의 가장 최근 리뷰 1건 반환
     * 리뷰가 없다면 null 반환
     */
    @GetMapping("/themes/one")
    public ResponseEntity<ResponseWrapper<List<ReviewDetailResDto>>> selectThemeReview(
            @RequestParam("themeId") String themeId) {

        List<ReviewDetailResDto> review = reviewService.selectThemeReview(themeId);

        return JsonResponse.ok("테마의 리뷰가 성공적으로 조회되었습니다.", review);
    }

    /**
     * 특정 테마의 리뷰 전체 조회 (회원 테마 상세 페이지)
     */
    @GetMapping("/themes/all")
    public ResponseEntity<ResponseWrapper<List<ReviewDetailResDto>>> selectThemeReviewList(
            @RequestParam("themeId") String themeId) {

        List<ReviewDetailResDto> reviewList = reviewService.selectThemeReviewList(themeId);

        return JsonResponse.ok("테마의 리뷰 목록이 성공적으로 조회되었습니다.", reviewList);
    }

    /**
     * 특정 테마의 내가 쓴 리뷰 조회 (회원 테마 상세 페이지)
     * 23.11.02 기준) 회원은 테마 1개 당 하나의 리뷰만 작성 가능 -> 조회 결과는 무조건 0개 또는 1개
     */
    @GetMapping("/themes/my")
    public ResponseEntity<ResponseWrapper<ReviewDetailResDto>> selectMyThemeReview(
            @RequestHeader("memberId") String memberId,
            @RequestParam("themeId") String themeId) {

        ReviewDetailResDto myReview = reviewService.selectMyThemeReview(UUID.fromString(memberId), themeId);

        return JsonResponse.ok("테마의 내가 쓴 리뷰가 성공적으로 조회되었습니다.", myReview);
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/{review-id}")
    public ResponseEntity<ResponseWrapper<Nullable>> deleteReview(
            @RequestHeader("memberId") String memberId,
            @PathVariable("review-id") Long reviewId) {

        reviewService.deleteReview(UUID.fromString(memberId), reviewId);

        return JsonResponse.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

    /**
     * 내가 쓴 리뷰 전체 조회 (마이페이지)
     */
    @GetMapping("/my")
    public ResponseEntity<ResponseWrapper<List<ReviewDetailResDto>>> selectMyReviewList(
            @RequestHeader("memberId") String memberId) {

        List<ReviewDetailResDto> myReviewList = reviewService.selectMyReviewList(UUID.fromString(memberId));

        return JsonResponse.ok("내가 쓴 리뷰 목록이 성공적으로 조회되었습니다.", myReviewList);
    }

    /**
     * 내 방탈출 기록 조회 (빌딩 페이지)
     */
    @GetMapping("/history")
    public ResponseEntity<ResponseWrapper<List<ThemeHistoryResDto>>> selectMyThemeHistory(
            @RequestHeader("memberId") String memberId) {

        List<ThemeHistoryResDto> myThemeHistory = reviewService.selectMyThemeHistory(UUID.fromString(memberId));

        return JsonResponse.ok("내 방탈출 히스토리가 성공적으로 조회되었습니다.", myThemeHistory);
    }

}
