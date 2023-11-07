package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.client.SearchServiceClient;
import com.rainbowgon.memberservice.domain.bookmark.client.dto.input.SearchThemeInDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.request.BookmarkUpdateReqDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkDetailResDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.error.exception.BookmarkNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final ProfileService profileService;
    private final SearchServiceClient searchServiceClient;
    private final RedisTemplate<String, String> stringRedisTemplate;

    @Transactional
    @Override
    public void updateBookmarkList(UUID memberId, BookmarkUpdateReqDto bookmarkUpdateReqDto) {

        // 요청 회원의 프로필 가져오기
        ProfileSimpleResDto profile = getProfile(memberId);

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();

        for (String themeId : bookmarkUpdateReqDto.getBookmarkThemeIdList()) {
            // redis key 설정
            String bookmarkKey = generateBookmarkKey(profile.getProfileId(), themeId);
            if (valueOperations.get(bookmarkKey) == null) { // 없으면 삽입 (북마크 등록)
                valueOperations.set(bookmarkKey, profile.getBookmarkNotificationStatus().name());
                // 테마별 예약 오픈 시간 가져와서 타임테이블에 추가하기
                String openTime = "0000"; // TODO themeId로 오픈 시간 정보 가져오기
                setOperations.add(openTime, themeId);
            } else { // 있으면 삭제 (북마크 해제)
                valueOperations.getAndDelete(bookmarkKey);
            }
        }

        // TODO redis에 북마크 수 업데이트
    }

    @Override
    public BookmarkSimpleResDto selectSimpleBookmarkList(UUID memberId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfile(memberId).getProfileId();

        // 요청 회원의 북마크 테마 목록 가져오기
        // TODO 최신순으로 20개까지 자르기

        // TODO 북마크 목록에서 테마 ID를 통해 각각의 테마 정보(아이디, 포스터, 테마명, 지점명) 가져오기 -> search-service

        // TODO 북마크 목록에서 테마 ID를 통해 각각의 리뷰 정보(평균 별점, 리뷰 수) 가져오기 -> redis


        // TODO pageInfo 추가
        return null;
    }

    @Override
    public BookmarkDetailResDto selectDetailBookmarkList(UUID memberId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfile(memberId).getProfileId();

        // 요청 회원의 북마크 테마 목록 가져오기
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Set<String> bookmarkKeyList = stringRedisTemplate.keys("BOOKMARK:" + profileId + "$*");

        // search-service -> 북마크 목록의 테마 ID를 통해 각각의 테마 정보(전체) 가져오기
        List<String> themeIdList = bookmarkKeyList.stream().map(this::getThemeId).collect(Collectors.toList());
        List<SearchThemeInDto> themeInfoList = searchServiceClient.getBookmarkThemeInfo(themeIdList);

        // TODO redis -> 북마크 목록의 테마 ID를 통해 각각의 테마 정보(평균 별점, 리뷰 수, 북마크 수) 가져오기


        // TODO 무한스크롤
        return null;
    }

    @Transactional
    @Override
    public String updateNotificationStatus(UUID memberId, String themeId) {

        // 요청 회원의 프로필 ID 가져오기
        Long profileId = getProfile(memberId).getProfileId();

        // 프로필 ID, 테마 ID로 북마크 알림 상태 가져오기
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String bookmarkKey = generateBookmarkKey(profileId, themeId);
        String notificationStatus = valueOperations.get(bookmarkKey);

        if (notificationStatus == null) {
            throw BookmarkNotFoundException.EXCEPTION;
        }

        // 현재 상태와 반대 상태로 변경하기
        String oppositeStatus = notificationStatus.equals("ON") ? "OFF" : "ON";
        valueOperations.set(bookmarkKey, oppositeStatus);

        return oppositeStatus;
    }

    /**
     * 회원 ID(uuid)로 프로필 조회
     */
    private ProfileSimpleResDto getProfile(UUID memberId) {
        return profileService.selectProfileByMember(memberId);
    }

    /**
     * 프로필 ID, 테마 ID로 redis 북마크 key 만들기
     */
    private String generateBookmarkKey(Long profileId, String themeId) {
        return new StringBuilder("BOOKMARK:").append(profileId.toString()).append("$").append(themeId).toString();
    }

    /**
     * redis 북마크 key에서 테마 ID 뽑아내기
     */
    private String getThemeId(String bookmarkKey) {
        return bookmarkKey.split("$")[1];
    }

}
