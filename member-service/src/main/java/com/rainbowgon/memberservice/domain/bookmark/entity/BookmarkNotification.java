package com.rainbowgon.memberservice.domain.bookmark.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "bookmark")
public class BookmarkNotification {

    @Id
    private Long bookmarkId;
//    private String themeId;
//    private Long profileId;

    @Builder
    public BookmarkNotification(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

//    @Builder
//    public BookmarkNotification(Long bookmarkId, String themeId, Long profileId) {
//        this.bookmarkId = bookmarkId;
//        this.themeId = themeId;
//        this.profileId = profileId;
//    }

}
