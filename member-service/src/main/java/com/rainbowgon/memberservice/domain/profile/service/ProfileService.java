package com.rainbowgon.memberservice.domain.profile.service;

import com.rainbowgon.memberservice.domain.member.dto.MemberDto;
import com.rainbowgon.memberservice.domain.member.dto.response.LoginResDto;
import com.rainbowgon.memberservice.domain.member.entity.Member;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.global.entity.NotificationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileService {

    LoginResDto createProfile(Member member, String fcmToken, String nickname, String profileImage);

    ProfileSimpleResDto selectProfileByMember(UUID memberId);

    void updateProfile(UUID memberId, Long profileId, String nickname, MultipartFile profileImage);

    NotificationStatus updateNotificationStatus(UUID memberId);

    NotificationStatus updateBookmarkNotificationStatus(UUID memberId);

    void deleteProfile(UUID memberId);

    MemberDto selectProfileById(Long profileId);

}
