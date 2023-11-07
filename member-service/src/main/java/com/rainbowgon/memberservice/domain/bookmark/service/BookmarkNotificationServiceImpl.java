package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.client.NotificationServiceClient;
import com.rainbowgon.memberservice.domain.bookmark.client.SearchServiceClient;
import com.rainbowgon.memberservice.domain.bookmark.client.dto.input.SearchSimpleInDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkNotificationServiceImpl implements BookmarkNotificationService {

    private final ProfileService profileService;
    @Qualifier("bookmarkRedisStringTemplate")
    private final RedisTemplate<String, String> bookmarkRedisStringTemplate;
    @Qualifier("fcmTokenRedisStringTemplate")
    private final RedisTemplate<Long, String> fcmTokenRedisStringTemplate;
    private final NotificationServiceClient notificationServiceClient;
    private final SearchServiceClient searchServiceClient;

    /**
     * redis 타임테이블에 새로운 key가 추가되는지 확인
     */
    @Override
    public void checkOpenTime() {

    }

    /**
     * openTime 10분 전에 알림 서버에 요청 보내기
     */
    @Scheduled(cron = "0 50 23 * * *") // 매일 오후 11시 50분에 알림 보내기
    @Override
    public void sendBookmarkNotificationList() {

        // redis에서 12시에 오픈되는 테마 ID 리스트 가져오기
        SetOperations<String, String> setOperations = bookmarkRedisStringTemplate.opsForSet();
        Set<String> themeIdList = setOperations.members("TIMETABLE:0000");

        // search-service에서 테마 ID를 통해 테마 정보(테마 이름, 지점명) 가져오기
        List<SearchSimpleInDto> themeInfoList = searchServiceClient.getBookmarkThemeSimpleInfo(themeIdList);

//        // redis에서 각각의 테마를 북마크 하고 있는 사용자 정보 가져오기
//        themeInfoList.stream().map(SearchSimpleInDto::getThemeId) // 테마 ID
//                .map(themeId -> bookmarkRedisStringTemplate.keys("BOOKMARK:*$" + themeId)) // 테마 ID가 포함된 북마크 키 리스트
//                .filter(Objects::nonNull)
//                .map(bookmarkKeySet -> bookmarkKeySet.stream()
//                                                    .map(bookmarkKey -> getProfileId(bookmarkKey)) // 북마크 키에서 프로필
//                                                    ID 가져오기
//                                                    .map(profileId -> getFcmToken(profileId))


    }

    /**
     * redis 북마크 key에서 프로필 ID 뽑아내기
     */
    private Long getProfileId(String bookmarkKey) {
        return Long.parseLong(bookmarkKey.split(":")[1].split("$")[0]);
    }

    /**
     * redis에서 프로필 ID(key)로 fcm token(value) 가져오기
     */
    private String getFcmToken(Long profileId) {
        ValueOperations<Long, String> valueOperations = fcmTokenRedisStringTemplate.opsForValue();
        return valueOperations.get(profileId);
    }
}
