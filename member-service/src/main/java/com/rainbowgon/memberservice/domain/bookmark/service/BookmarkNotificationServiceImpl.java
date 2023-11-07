package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.client.NotificationServiceClient;
import com.rainbowgon.memberservice.domain.bookmark.client.SearchServiceClient;
import com.rainbowgon.memberservice.domain.bookmark.client.dto.input.SearchSimpleInDto;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkNotificationServiceImpl implements BookmarkNotificationService {

    private final ProfileService profileService;
    private final RedisTemplate<String, String> stringRedisTemplate;
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
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        Set<String> themeIdList = setOperations.members("TIMETABLE:0000");

        // search-service에서 테마 ID를 통해 테마 정보(테마 이름, 지점명) 가져오기
        List<SearchSimpleInDto> themeInfoList = searchServiceClient.getBookmarkThemeSimpleInfo(themeIdList);

        // redis에서 각각의 테마를 북마크 하고 있는 사용자 정보 가져오기
        themeInfoList.stream().map(SearchSimpleInDto::getThemeId) // 테마 ID
                .map(themeId -> stringRedisTemplate.keys("BOOKMARK:*$" + themeId)) // 테마 ID가 포함된 북마크 리스트
                .filter(Objects::nonNull)
                .map(bookmarkKeySet -> bookmarkKeySet.stream()
                                                    .map(this::getProfileId)
                        .)

//        themeIdList.stream()
//                .map(themeId -> stringRedisTemplate.keys("BOOKMARK:*$" + themeId)) // 북마크 키 리스트 가져오기
//                .map(bookmarkKeyList -> bookmarkKeyList.stream()
//                                     .map(bookmarkKey -> getProfileId(bookmarkKey)) // 북마크 키에서 프로필 키 가져오기
//                        .collect(Collectors.toList())
//                )


    }


    /**
     * redis 북마크 key에서 프로필 ID 뽑아내기
     */
    private Long getProfileId(String bookmarkKey) {
        return Long.parseLong(bookmarkKey.split(":")[1].split("$")[0]);
    }

    /**
     * 프로필 ID로 fcm token 가져오기
     */
    private String getFcmToken(Long profileId) {

    }
}
