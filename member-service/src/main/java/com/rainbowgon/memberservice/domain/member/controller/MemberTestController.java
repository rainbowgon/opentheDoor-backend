package com.rainbowgon.memberservice.domain.member.controller;

import com.rainbowgon.memberservice.global.client.NotificationServiceClient;
import com.rainbowgon.memberservice.global.redis.service.BookmarkRedisService;
import com.rainbowgon.memberservice.global.redis.service.SortingRedisService;
import com.rainbowgon.memberservice.global.redis.service.TokenRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberTestController {

    private final NotificationServiceClient notificationServiceClient;

    private final BookmarkRedisService bookmarkRedisService;
    private final SortingRedisService sortingRedisService;
    private final TokenRedisService tokenRedisService;


    /**
     * 통신 테스트를 위한 API
     */
    @GetMapping("/test")
    public ResponseEntity<String> testNotificationService() {

        String testResponse = notificationServiceClient.testNotificationService();

        return ResponseEntity.ok("notification-service에서 받은 응답 : " + testResponse);
    }

    /**
     * redis server 조회를 위한 API
     */
    @GetMapping("/redis")
    public ResponseEntity<String> selectAllRedis() {

        Integer bookmarkKeys = bookmarkRedisService.keysAll();
        Integer sortingKeys = sortingRedisService.keysAll();
        Integer tokenKeys = tokenRedisService.keysAll();

        StringBuilder sb = new StringBuilder();
        sb.append("bookmark redis keys : ").append(bookmarkKeys).append("\n");
        sb.append("sorting redis keys : ").append(sortingKeys).append("\n");
        sb.append("token redis keys : ").append(tokenKeys).append("\n");

        return ResponseEntity.ok(sb.toString());
    }

    /**
     * redis server 삭제를 위한 API
     */
    @DeleteMapping("/redis")
    public ResponseEntity<String> deleteAllRedis() {

        Boolean isBookmarkRedisDeleted = bookmarkRedisService.flushAll();
        Boolean isSortingRedisDeleted = sortingRedisService.flushAll();
        Boolean isTokenRedisDeleted = tokenRedisService.flushAll();

        StringBuilder sb = new StringBuilder();
        sb.append("bookmark redis deleted : ").append(isBookmarkRedisDeleted).append("\n");
        sb.append("sorting redis deleted : ").append(isSortingRedisDeleted).append("\n");
        sb.append("token redis deleted : ").append(isTokenRedisDeleted).append("\n");

        return ResponseEntity.ok(sb.toString());
    }

}
