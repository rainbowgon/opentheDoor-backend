package com.rainbowgon.memberservice.domain.review.service;

import com.rainbowgon.memberservice.domain.review.dto.request.ReviewCreateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.request.ReviewUpdateReqDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ReviewDetailResDto;
import com.rainbowgon.memberservice.domain.review.dto.response.ThemeHistoryResDto;
import com.rainbowgon.memberservice.domain.review.entity.Review;
import com.rainbowgon.memberservice.domain.review.repository.ReviewRepository;
import com.rainbowgon.memberservice.global.client.ReservationServiceClient;
import com.rainbowgon.memberservice.global.client.SearchServiceClient;
import com.rainbowgon.memberservice.global.error.exception.RedisErrorException;
import com.rainbowgon.memberservice.global.error.exception.ReviewNotFoundException;
import com.rainbowgon.memberservice.global.error.exception.ReviewUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final SearchServiceClient searchServiceClient;
    private final ReservationServiceClient reservationServiceClient;
    
    @Qualifier("sortingRedisStringTemplate")
    private final RedisTemplate<String, String> sortingRedisStringTemplate;

    @Transactional
    @Override
    public ReviewDetailResDto createReview(UUID memberId, ReviewCreateReqDto reviewCreateReqDto) {

        // TODO 예약 내역이 있는 리뷰인지 확인 (인증 뱃지)
        // 예약 내역 있으면, 예약 날짜/시간이랑 입력값이랑 비교?
        Long reservationId = null;

        // 리뷰 생성
        Review review = reviewRepository.save(
                Review.builder()
                        .memberId(memberId)
                        .themeId(reviewCreateReqDto.getThemeId())
                        .reservationId(reservationId)
                        .rating(reviewCreateReqDto.getRating())
                        .isEscaped(reviewCreateReqDto.getIsEscaped())
                        .myLevel(reviewCreateReqDto.getMyLevel())
                        .hintCount(reviewCreateReqDto.getHintCount())
                        .content(reviewCreateReqDto.getContent())
                        .isSpoiler(reviewCreateReqDto.getIsSpoiler())
                        .performedDate(reviewCreateReqDto.getPerformedDate())
                        .performedTime(reviewCreateReqDto.getPerformedTime())
                        .performedHeadcount(reviewCreateReqDto.getPerformedHeadcount())
                        .build());

        // sorting redis 반영
        createSortingRedis(review.getThemeId(), review.getRating());

        return ReviewDetailResDto.from(review);
    }

    @Transactional
    @Override
    public ReviewDetailResDto updateReview(UUID memberId, ReviewUpdateReqDto reviewUpdateReqDto) {

        // 리뷰 객체 가져오기
        Review review =
                reviewRepository.findById(reviewUpdateReqDto.getReviewId()).orElseThrow(ReviewNotFoundException::new);

        // 유효한 요청인지 확인
        checkValidAccess(memberId, review.getMemberId());

        // sorting redis 업데이트
        updateSortingRedis(review.getThemeId(), review.getRating(), reviewUpdateReqDto.getRating());

        // 리뷰 업데이트
        review.updateReview(reviewUpdateReqDto);

        return ReviewDetailResDto.from(review);
    }

    @Override
    public ReviewDetailResDto selectThemeReview(String themeId) {

        // 테마 ID로 최신 리뷰 1건 조회
        Optional<Review> review = reviewRepository.findTopByThemeIdOrderByCreatedAtDesc(themeId);

        // 해당 테마에 리뷰가 1건도 없으면 null 반환
        return review.map(ReviewDetailResDto::from).orElse(null);
    }

    @Override
    public List<ReviewDetailResDto> selectThemeReviewList(String themeId) {

        // 테마 ID로 리뷰 전체 조회
        List<Review> reviewList = reviewRepository.findAllByThemeId(themeId);

        // TODO pagination

        return reviewList.stream().map(ReviewDetailResDto::from).collect(Collectors.toList());
    }

    @Override
    public ReviewDetailResDto selectMyThemeReview(UUID memberId, String themeId) {

        // 테마 ID와 회원 ID로 리뷰 조회
        Optional<Review> review = reviewRepository.findByThemeIdAndMemberId(themeId, memberId);

        // 해당 테마에 요청 회원이 작성한 리뷰가 없으면 null 반환
        return review.map(ReviewDetailResDto::from).orElse(null);
    }

    @Transactional
    @Override
    public void deleteReview(UUID memberId, Long reviewId) {

        // 리뷰 객체 가져오기
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        // 유효한 요청인지 확인
        checkValidAccess(memberId, review.getMemberId());

        // sorting redis 반영
        deleteSortingRedis(review.getThemeId(), review.getRating());

        // 리뷰 삭제
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewDetailResDto> selectMyReviewList(UUID memberId) {

        // 회원 ID로 리뷰 목록 조회
        List<Review> reviewList = reviewRepository.findAllByMemberId(memberId);

        // TODO pagination

        return reviewList.stream().map(ReviewDetailResDto::from).collect(Collectors.toList());
    }

    /**
     * 방탈출 내역 확인 경우의 수
     * 1. 예약 내역이 있고, 리뷰도 있는 경우 -> 예약 서비스에서 확인 가능
     * 2. 예약 내역이 있고, 리뷰는 없는 경우 -> 예약 서비스에서 확인 가능
     * 3. 예약 내역이 없고, 리뷰는 있는 경우 -> 멤버 서비스에서 확인 가능(여기)
     * 4. 예약 내역도 없고, 리뷰도 없는 경우 -> 우리 서비스에서 확인 불가
     */
    @Override
    public List<ThemeHistoryResDto> selectMyThemeHistory(UUID memberId) {

        // TODO 회원 ID로 예약 서버에서 데이터(예약 ID, 테마 ID) 가져오기

        // 회원 ID로 리뷰 목록 조회
        List<Review> reviewList = reviewRepository.findAllByMemberId(memberId);
        List<String> themeIdList = reviewList.stream().map(Review::getThemeId).collect(Collectors.toList());

        // 테마 ID로 검색 서버에서 테마 데이터(포스터, 테마명) 가져오기
        // 23.11.12) 요청 보낸 id 순서대로 응답이 온다는 보장은 없지만 일단 진행...하려고 했지만 복잡해서 보류
//        searchServiceClient.getBookmarkThemeSimpleInfo(themeIdList).stream()
//                .map(themeSimpleInDto -> ThemeHistoryResDto.of(themeSimpleInDto);

        return null;
    }

    /**
     * 요청 회원의 ID와 리뷰의 회원 ID가 같은지 확인
     */
    private void checkValidAccess(UUID memberId, UUID reviewMemberId) {
        if (!memberId.equals(reviewMemberId)) {
            throw ReviewUnauthorizedException.EXCEPTION;
        }
    }

    /**
     * 리뷰 생성 시 sorting redis 업데이트
     * - REVIEW count += 1
     * - RATING 재계산
     */
    private void createSortingRedis(String themeId, Double rating) {

        ZSetOperations<String, String> zSetOperations = sortingRedisStringTemplate.opsForZSet();
        Double ratingScore = zSetOperations.score("RATING", themeId);

        if (ratingScore == null) {
            zSetOperations.add("REVIEW", themeId, 1);
            zSetOperations.add("RATING", themeId, rating);
        } else {
            // 기존 리뷰가 있으면, 리뷰 수 + 1
            zSetOperations.incrementScore("REVIEW", themeId, 1);
            Double reviewCount = zSetOperations.score("REVIEW", themeId);
            // 평균 별점 재계산
            double delta = (rating - ratingScore) / reviewCount;
            zSetOperations.incrementScore("RATING", themeId, Math.round(delta * 10.0 / 10.0));
        }
    }

    /**
     * 리뷰 수정 시 sorting redis 업데이트
     * - 별점 달라졌을 경우, RATING 재계산
     */
    private void updateSortingRedis(String themeId, Double originRating, Double newRating) {

        ZSetOperations<String, String> zSetOperations = sortingRedisStringTemplate.opsForZSet();
        Double ratingScore = zSetOperations.score("RATING", themeId);

        // 해당 테마의 평균 별점 데이터가 없으면 레디스 에러
        if (ratingScore == null) {
            throw RedisErrorException.EXCEPTION;
        }

        // 평균 별점 재계산
        Double reviewCount = zSetOperations.score("REVIEW", themeId);
        double delta = (newRating - originRating) / reviewCount;
        zSetOperations.incrementScore("RATING", themeId, Math.round(delta * 10.0 / 10.0));
    }

    /**
     * 리뷰 삭제 시 sorting redis 업데이트
     * - REVIEW count -= 1
     * - RATING 재계산
     */
    private void deleteSortingRedis(String themeId, Double rating) {

        ZSetOperations<String, String> zSetOperations = sortingRedisStringTemplate.opsForZSet();
        Double ratingScore = zSetOperations.score("RATING", themeId);

        // 해당 테마의 평균 별점 데이터가 없으면 레디스 에러
        if (ratingScore == null) {
            throw RedisErrorException.EXCEPTION;
        }

        // 리뷰 수 -1
        zSetOperations.incrementScore("REVIEW", themeId, -1);
        Double reviewCount = zSetOperations.score("REVIEW", themeId);
        // 평균 별점 재계산
        double delta = (ratingScore - rating) / reviewCount;
        zSetOperations.incrementScore("RATING", themeId, Math.round(delta * 10.0 / 10.0));
    }
}
