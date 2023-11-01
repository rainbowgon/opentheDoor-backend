package com.rainbowgon.member.domain.profile.service;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.member.global.entity.NotificationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ProfileService {

    ProfileSimpleResDto createProfile(Member member, String nickname, String profileImage);

    ProfileSimpleResDto selectProfileByMember(UUID memberId);

    Boolean updateProfile(UUID memberId, Long profileId, String nickname, MultipartFile profileImage);

    NotificationStatus updateNotificationStatus(UUID memberId);

    NotificationStatus updateBookmarkNotificationStatus(UUID memberId);

    void delectProfile(UUID memberId);

}
