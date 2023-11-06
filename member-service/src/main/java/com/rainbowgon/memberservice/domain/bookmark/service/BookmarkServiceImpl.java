package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.dto.request.BookmarkUpdateReqDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkDetailResDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkSimpleResDto;
import com.rainbowgon.memberservice.domain.bookmark.entity.Bookmark;
import com.rainbowgon.memberservice.domain.bookmark.entity.BookmarkNotification;
import com.rainbowgon.memberservice.domain.bookmark.repository.BookmarkRepository;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.entity.NotificationStatus;
import com.rainbowgon.memberservice.global.entity.ValidStatus;
import com.rainbowgon.memberservice.global.error.exception.BookmarkNotFoundException;
import com.rainbowgon.memberservice.global.error.exception.BookmarkUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ProfileService profileService;

    // key: 오픈 시간 (4자리 숫자) ex. 1030(오전 10시 반), 2400(밤 12시), 1850(저녁 6시 50분)
//    private final RedisTemplate<String, BookmarkNotification> redisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    @Override
    public void updateBookmarkList(UUID memberId, BookmarkUpdateReqDto bookmarkUpdateReqDto) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfileId(memberId);

        for (String themeId : bookmarkUpdateReqDto.getBookmarkThemeIdList()) {
            // 프로필 ID와 테마 ID로 북마크 찾기
            bookmarkRepository.findByProfileIdAndThemeId(profileId, themeId).ifPresentOrElse(
                    bookmark -> updateBookmark(bookmark, "2400"), // 이미 존재하는 북마크라면, valid 상태 변경
                    () -> createBookmark(profileId, themeId)); // 존재하지 않는 북마크라면, 새로 생성
        }

        // TODO redis에 북마크 수 업데이트
    }

    /**
     * 이미 존재하는 북마크일 경우
     */
    private void updateBookmark(Bookmark bookmark, String openTime) {

        if (bookmark.getIsValid().equals(ValidStatus.VALID)) { // status == valid
            bookmark.updateIsValid(ValidStatus.DELETED);
            redisTemplate.opsForSet().remove(
                    openTime, BookmarkNotification.builder().bookmarkId(bookmark.getId()).build());
        } else { // status == deleted
            bookmark.updateIsValid(ValidStatus.VALID);
            createBookmarkInRedis(bookmark);
        }
    }

    /**
     * 존재하지 않는 북마크일 경우
     * RDB, Redis insert
     */
    private void createBookmark(Long profileId, String themeId) {

        // rdb
        Bookmark bookmark = bookmarkRepository.save(
                Bookmark.builder()
                        .profileId(profileId)
                        .themeId(themeId)
                        .build());

        // redis
        createBookmarkInRedis(bookmark);
    }

    /**
     * 북마크 생성 (Redis)
     */
    private void createBookmarkInRedis(Bookmark bookmark) {

        // TODO 실제 예약 오픈 시간으로 수정
        redisTemplate.opsForSet().add(
                "2400", BookmarkNotification.builder()
                        .bookmarkId(bookmark.getId())
                        .build());
    }

    @Override
    public BookmarkSimpleResDto selectSimpleBookmarkList(UUID memberId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfileId(memberId);

        // 요청 회원의 북마크 테마 목록 가져오기
        // TODO 최신순으로 20개까지 자르기
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByProfileId(profileId);

        // TODO 북마크 목록에서 테마 ID를 통해 각각의 테마 정보(아이디, 포스터, 테마명, 지점명) 가져오기 -> search-service

        // TODO 북마크 목록에서 테마 ID를 통해 각각의 리뷰 정보(평균 별점, 리뷰 수) 가져오기 -> redis


        // TODO pageInfo 추가
        return null;
    }

    @Override
    public BookmarkDetailResDto selectDetailBookmarkList(UUID memberId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfileId(memberId);

        // 요청 회원의 북마크 테마 목록 가져오기
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByProfileId(profileId);

        // TODO 북마크 목록에서 테마 ID를 통해 각각의 테마 정보(전체) 가져오기 -> search-service

        // TODO 북마크 목록에서 테마 ID를 통해 각각의 리뷰 정보(평균 별점, 리뷰 수) 가져오기 -> redis

        // TODO 북마크 목록에서 테마 ID를 통해 각각의 북마크 정보(북마크 수) 가져오기 -> redis


        // TODO 무한스크롤
        return null;
    }

    @Transactional
    @Override
    public NotificationStatus updateNotificationStatus(UUID memberId, Long bookmarkId) {

        // 북마크 객체 가져오기
        Bookmark bookmark =
                bookmarkRepository.findById(bookmarkId).orElseThrow(BookmarkNotFoundException::new);

        // 유효한 요청인지 확인
        checkValidAccess(getProfileId(memberId), bookmark.getProfileId());

        // 현재 알림 설정 상태 가져오기
        NotificationStatus status = bookmark.getNotificationStatus();

        // 현재 상태와 반대 상태로 변경하기
        bookmark.updateNotificationStatus(status.equals(NotificationStatus.ON) ? NotificationStatus.OFF :
                                                  NotificationStatus.ON);

        return bookmark.getNotificationStatus();
    }

    /**
     * 회원 ID(uuid)로 프로필 아이디 조회
     */
    private Long getProfileId(UUID memberId) {
        ProfileSimpleResDto profile = profileService.selectProfileByMember(memberId);
        return profile.getProfileId();
    }

    /**
     * 요청 회원의 프로필 ID와 북마크의 프로필 ID가 같은지 확인
     */
    private void checkValidAccess(Long requestProfileId, Long bookmarkProfileId) {
        if (!requestProfileId.equals(bookmarkProfileId)) {
            throw BookmarkUnauthorizedException.EXCEPTION;
        }
    }

}
