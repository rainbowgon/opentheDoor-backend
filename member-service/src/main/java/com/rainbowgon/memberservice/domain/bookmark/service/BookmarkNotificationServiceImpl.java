package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.client.NotificationServiceClient;
import com.rainbowgon.memberservice.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkNotificationServiceImpl implements BookmarkNotificationService {

    private final ProfileService profileService;
    private final RedisTemplate<String, String> stringRedisTemplate;
    private final NotificationServiceClient notificationServiceClient;

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

//        //
//
//        // redis에서 12시에 오픈되는 테마 ID 리스트 가져오기
//        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
//        Set<String> themeIdList = setOperations.members("TIMETABLE:0000");
//
//        // redis에서 해당 테마를 북마크 하고 있는 사용자 정보 가져오기
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
    private String getProfileId(String bookmarkKey) {
        return bookmarkKey.split(":")[1].split("$")[0];
    }
}
