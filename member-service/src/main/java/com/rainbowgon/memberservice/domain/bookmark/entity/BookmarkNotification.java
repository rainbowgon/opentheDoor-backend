package com.rainbowgon.memberservice.domain.bookmark.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "openTime")
public class BookmarkNotification {

    @Id
    private String openTime; // 오픈 시간 (4자리 숫자) ex. 1030(오전 10시 반), 2400(밤 12시), 1850(저녁 6시 50분)
    @Indexed
    private Long bookmarkId;
    private String themeId;
    private Long profileId;

    @Builder
    public BookmarkNotification(String openTime, Long bookmarkId, String themeId, Long profileId) {
        this.openTime = openTime;
        this.bookmarkId = bookmarkId;
        this.themeId = themeId;
        this.profileId = profileId;
    }

}
