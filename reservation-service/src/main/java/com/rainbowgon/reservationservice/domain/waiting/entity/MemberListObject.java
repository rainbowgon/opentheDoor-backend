package com.rainbowgon.reservationservice.domain.waiting.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

/**
 * 하나의 테마애 대해서 예약 대기를 한 사람들 리스트
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("MemberList")
public class MemberListObject {

    @Id
    private String themeKey;
    private List<String> memberList;

    public MemberListObject(String themeKey) {
        this.themeKey = themeKey;
        this.memberList = new ArrayList<>();
    }
}
