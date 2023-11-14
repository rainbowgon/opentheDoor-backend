package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.dto.request.BookmarkUpdateReqDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkDetailResDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import com.rainbowgon.memberservice.global.client.SearchServiceClient;
import com.rainbowgon.memberservice.global.client.dto.input.ThemeDetailInDto;
import com.rainbowgon.memberservice.global.client.dto.input.ThemeSimpleInDto;
import com.rainbowgon.memberservice.global.client.dto.output.ThemeListOutDto;
import com.rainbowgon.memberservice.global.error.exception.BookmarkNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
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

    @Qualifier("bookmarkRedisStringTemplate")
    private final RedisTemplate<String, String> bookmarkRedisStringTemplate;
    @Qualifier("sortingRedisStringTemplate")
    private final RedisTemplate<String, String> sortingRedisStringTemplate;

    @Transactional
    @Override
    public void updateBookmarkList(UUID memberId, BookmarkUpdateReqDto bookmarkUpdateReqDto) {

        // 북마크 redis
        ValueOperations<String, String> bookmarkOps = bookmarkRedisStringTemplate.opsForValue();
        // 타임테이블 redis
        SetOperations<String, String> openTimeOps = bookmarkRedisStringTemplate.opsForSet();

        // 요청 회원의 프로필 가져오기
        ProfileSimpleResDto profile = getProfile(memberId);

        for (String themeId : bookmarkUpdateReqDto.getBookmarkThemeIdList()) {

            // redis key 설정
            String bookmarkKey = generateBookmarkKey(memberId, themeId);

            // 기존에 존재하는 북마크이면 삭제, 없으면 삽입
            if (bookmarkOps.get(bookmarkKey) == null) {
                bookmarkOps.set(bookmarkKey, profile.getBookmarkNotificationStatus().name()); // 북마크 등록
                String openTime = "0000"; // TODO themeId로 오픈 시간 정보 가져오기
                openTimeOps.add(openTime, themeId); // 오픈 시간 타임 테이블에 추가
                updateBookmarkCount(themeId, 1); // 테마 북마크 수 +1
            } else {
                bookmarkOps.getAndDelete(bookmarkKey); // 북마크 해제
                updateBookmarkCount(themeId, -1); // 테마 북마크 수 -1
            }

        }
    }

    @Override
    public List<BookmarkSimpleResDto> selectSimpleBookmarkList(UUID memberId) {

        // 요청 회원의 북마크 테마 목록 가져오기
        // TODO 최신순으로 20개까지 자르기
        Set<String> bookmarkKeyList = bookmarkRedisStringTemplate.keys("BOOKMARK:" + memberId + "$*");

        if (bookmarkKeyList == null) {
            return null;
        }

        // search-service -> 북마크 목록의 테마 ID를 통해 각각의 테마 정보(아이디, 포스터, 테마명, 지점명, 평균 별점, 리뷰 수) 가져오기
        List<String> themeIdList = bookmarkKeyList.stream().map(this::getThemeId).collect(Collectors.toList());

        List<ThemeSimpleInDto> themeList =
                searchServiceClient.getBookmarkThemeSimpleInfo(ThemeListOutDto.builder().themeIdList(themeIdList).build());

        return themeList.stream().map(BookmarkSimpleResDto::from).collect(Collectors.toList());
    }

    @Override
    public List<BookmarkDetailResDto> selectDetailBookmarkList(UUID memberId) {

        // 요청 회원의 북마크 테마 목록 가져오기
        // TODO pagination
        Set<String> bookmarkKeyList = bookmarkRedisStringTemplate.keys("BOOKMARK:" + memberId + "$*");

        if (bookmarkKeyList == null) {
            return null;
        }

        // search-service -> 북마크 목록의 테마 ID를 통해 각각의 테마 정보(전체 + 평균 별점, 리뷰 수, 북마크 수) 가져오기
        List<String> themeIdList = bookmarkKeyList.stream().map(this::getThemeId).collect(Collectors.toList());
        
        List<ThemeDetailInDto> themeList =
                searchServiceClient.getBookmarkThemeDetailInfo(ThemeListOutDto.builder().themeIdList(themeIdList).build());

        return themeList.stream().map(BookmarkDetailResDto::from).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String updateNotificationStatus(UUID memberId, String themeId) {

        // 회원 ID, 테마 ID로 북마크 알림 상태 가져오기
        ValueOperations<String, String> valueOperations = bookmarkRedisStringTemplate.opsForValue();
        String bookmarkKey = generateBookmarkKey(memberId, themeId);
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
     * 회원 ID로 해당 회원의 북마크 전체 삭제
     */
    @Override
    public void deleteBookmark(UUID memberId) {

        // 회원의 북마크 목록 가져오기
        Set<String> bookmarkKeyList = bookmarkRedisStringTemplate.keys("BOOKMARK:" + memberId + "$*");

        // 북마크 목록 전체 삭제
        bookmarkRedisStringTemplate.delete(bookmarkKeyList);
    }

    /**
     * 회원 ID(uuid)로 프로필 조회
     */
    private ProfileSimpleResDto getProfile(UUID memberId) {
        return profileService.selectProfileByMember(memberId);
    }

    /**
     * 회원 ID, 테마 ID로 북마크 redis key 만들기
     */
    private String generateBookmarkKey(UUID memberId, String themeId) {
        return new StringBuilder("BOOKMARK:").append(memberId.toString()).append("$").append(themeId).toString();
    }

    /**
     * redis 북마크 key에서 테마 ID 뽑아내기
     */
    private String getThemeId(String bookmarkKey) {
        return bookmarkKey.split("\\$")[1];
    }

    /**
     * sorting redis 북마크 수 업데이트
     */
    private void updateBookmarkCount(String themeId, double count) {

        ZSetOperations<String, String> zSetOperations = sortingRedisStringTemplate.opsForZSet();
        Double themeExists = zSetOperations.score("BOOKMARK", themeId);

        // 테마 북마크 수가 없으면 새로 추가, 있으면 count(1 또는 -1)만큼 증가
        if (themeExists == null) {
            zSetOperations.add("BOOKMARK", themeId, count);
        } else {
            zSetOperations.incrementScore("BOOKMARK", themeId, count);
        }
    }

}
