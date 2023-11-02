package com.rainbowgon.member.domain.review.service;

import com.rainbowgon.member.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.member.domain.profile.service.ProfileService;
import com.rainbowgon.member.domain.review.dto.request.ReviewCreateReqDto;
import com.rainbowgon.member.domain.review.dto.request.ReviewUpdateReqDto;
import com.rainbowgon.member.domain.review.dto.response.MyReviewDetailResDto;
import com.rainbowgon.member.domain.review.dto.response.ReviewDetailResDto;
import com.rainbowgon.member.domain.review.dto.response.ThemeHistoryResDto;
import com.rainbowgon.member.domain.review.entity.Review;
import com.rainbowgon.member.domain.review.repository.ReviewRepository;
import com.rainbowgon.member.global.error.exception.ReviewNotFoundException;
import com.rainbowgon.member.global.error.exception.ReviewUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ProfileService profileService;

    @Transactional
    @Override
    public MyReviewDetailResDto createReview(UUID memberId, ReviewCreateReqDto reviewCreateReqDto) {

        // TODO 예약 내역이 있는 리뷰인지 확인 (인증 뱃지)
        // 예약 내역 있으면, 예약 날짜/시간이랑 입력값이랑 비교?
        Long reservationId = null;

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfileId(memberId);

        // 리뷰 생성
        Review review = reviewRepository.save(
                Review.builder()
                        .profileId(profileId)
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

        // TODO redis에 리뷰 수, 평균 별점 업데이트

        return MyReviewDetailResDto.from(review);
    }

    @Transactional
    @Override
    public MyReviewDetailResDto updateReview(UUID memberId, ReviewUpdateReqDto reviewUpdateReqDto) {

        // 리뷰 객체 가져오기
        Review review =
                reviewRepository.findById(reviewUpdateReqDto.getReviewId()).orElseThrow(ReviewNotFoundException::new);

        // 유효한 요청인지 확인
        checkValidAccess(getProfileId(memberId), review.getProfileId());

        // 리뷰 업데이트
        review.updateReview(reviewUpdateReqDto);

        // TODO redis에 평균 별점 업데이트

        return MyReviewDetailResDto.from(review);
    }

    @Override
    public ReviewDetailResDto selectThemeReview(Long themeId) {

        // 테마 ID로 리뷰 1건 조회
        Optional<Review> review = reviewRepository.findTopByThemeIdOrderByCreatedAtDesc(themeId);

        // 해당 테마에 리뷰가 1건도 없으면 null 반환
        return review.map(ReviewDetailResDto::from).orElse(null);
    }

    @Override
    public List<ReviewDetailResDto> selectThemeReviewList(Long themeId) {

        // 테마 ID로 리뷰 전체 조회
        List<Review> reviewList = reviewRepository.findAllByThemeId(themeId);

        // TODO pageInfo, 무한스크롤

        return null;
    }

    @Override
    public MyReviewDetailResDto selectMyThemeReview(UUID memberId, Long themeId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfileId(memberId);

        // 테마 ID와 프로필 ID로 리뷰 조회
        Optional<Review> review = reviewRepository.findByThemeIdAndProfileId(themeId, profileId);

        // 해당 테마에 요청 회원이 작성한 리뷰가 없으면 null 반환
        return review.map(MyReviewDetailResDto::from).orElse(null);
    }

    @Transactional
    @Override
    public void deleteReview(UUID memberId, Long reviewId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfileId(memberId);

        // 리뷰 객체 가져오기
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        // 유효한 요청인지 확인
        checkValidAccess(getProfileId(memberId), review.getProfileId());

        // 리뷰 삭제
        reviewRepository.delete(review);

        // TODO redis에 리뷰 수, 평균 별점 업데이트
    }

    @Override
    public List<MyReviewDetailResDto> selectMyReviewList(UUID memberId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfileId(memberId);

        // 프로필 ID로 리뷰 목록 조회
        List<Review> reviewList = reviewRepository.findAllByProfileId(profileId);

        // TODO pageInfo, 무한스크롤

        return reviewList.stream().map(MyReviewDetailResDto::from).collect(Collectors.toList());
    }

    /**
     * 방탈출 내역 확인 경우의 수
     * 1. 예약 내역이 있고, 리뷰도 있는 경우 -> 예약 서비스에서 확인 가능
     * 2. 예약 내역이 있고, 리뷰는 없는 경우 -> 예약 서비스에서 확인 가능
     * 3. 예약 내역이 없고, 리뷰는 있는 경우 -> 멤버 서비스에서 확인 가능(여기)
     * 4. 예약 내역도 없고, 리뷰도 없는 경우 -> 우리 서비스에서 확인 불가
     */
    @Override
    public List<ThemeHistoryResDto> selectThemeHistory(Long profileId) {

        // TODO 프로필 ID로 예약 서버에서 데이터(예약 ID, 테마 ID) 가져오기
        List<Long> themeIdList = null;

        // 프로필 ID로 리뷰 목록 조회
        List<Review> reviewList = reviewRepository.findAllByProfileId(profileId);

        // 중복 제거 (1번 경우)

        // TODO 테마 ID로 검색 서버에서 테마 데이터(포스터, 테마명) 가져오기

        return null;
    }

    /**
     * 회원 ID(uuid)로 프로필 아이디 조회
     */
    private Long getProfileId(UUID memberId) {
        ProfileSimpleResDto profile = profileService.selectProfileByMember(memberId);
        return profile.getProfileId();
    }

    /**
     * 요청 회원의 프로필 ID와 리뷰의 프로필 ID가 같은지 확인
     */
    private void checkValidAccess(Long requestProfileId, Long reviewProfileId) {
        if (!requestProfileId.equals(reviewProfileId)) {
            throw ReviewUnauthorizedException.EXCEPTION;
        }
    }
}
