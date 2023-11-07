package com.rainbowgon.memberservice.domain.member.dto.request;

import com.rainbowgon.memberservice.domain.member.entity.Provider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateReqDto {

    private String name;
    private String phoneNumber;
    private Provider provider;
    private String providerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String fcmToken;

    // Profile에 저장되는 정보
    private String nickname;
    private String profileImage;

}
