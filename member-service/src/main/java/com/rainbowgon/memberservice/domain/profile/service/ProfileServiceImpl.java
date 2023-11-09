package com.rainbowgon.memberservice.domain.profile.service;

import com.rainbowgon.memberservice.domain.member.entity.Member;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.entity.Profile;
import com.rainbowgon.memberservice.domain.profile.repository.ProfileRepository;
import com.rainbowgon.memberservice.global.entity.NotificationStatus;
import com.rainbowgon.memberservice.global.error.exception.ProfileNotFoundException;
import com.rainbowgon.memberservice.global.error.exception.ProfileUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    @Override
    public ProfileSimpleResDto createProfile(Member member, String nickname, String profileImage) {

        // 프로필 생성
        Profile profile = profileRepository.save(
                Profile.builder()
                        .member(member)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .build());

        return ProfileSimpleResDto.from(profile);
    }

    @Override
    public ProfileSimpleResDto selectProfileByMember(UUID memberId) {
        return ProfileSimpleResDto.from(getProfileByMemberId(memberId));
    }

    @Transactional
    @Override
    public Boolean updateProfile(UUID memberId, Long profileId, String nickname, MultipartFile profileImage) {

        // 프로필 객체 찾기
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        // 유효한 요청인지 확인
        checkValidAccess(profile.getMember().getId(), memberId);

        // 닉네임 수정
        profile.updateNickname(nickname);

        // 프로필 사진 변경됐다면 수정
        if (profileImage != null) {
            // TODO 수정한 프로필 이미지 s3 업로드
            String profileImageUrl = null;
            profile.updateProfileImage(profileImageUrl);
        }

        return true;
    }

    @Transactional
    @Override
    public NotificationStatus updateNotificationStatus(UUID memberId) {

        // 프로필 객체 가져오기
        Profile profile = getProfileByMemberId(memberId);

        // 현재 전체 알림 설정 상태 가져오기
        NotificationStatus status = profile.getNotificationStatus();
        log.info("[ProfileServiceImpl] 현재 전체 알림 세팅값 = " + status);

        // 현재 상태와 반대 상태로 변경하기
        profile.updateNotificationStatus(
                status.equals(NotificationStatus.ON) ? NotificationStatus.OFF : NotificationStatus.ON);

        return profile.getNotificationStatus();
    }

    @Transactional
    @Override
    public NotificationStatus updateBookmarkNotificationStatus(UUID memberId) {

        // 프로필 객체 가져오기
        Profile profile = getProfileByMemberId(memberId);

        // 현재 북마크 알림 설정 상태 가져오기
        NotificationStatus status = profile.getBookmarkNotificationStatus();
        log.info("[ProfileServiceImpl] 현재 북마크 알림 세팅값 = " + status);

        // 현재 상태와 반대 상태로 변경하기
        profile.updateBookmarkNotificationStatus(
                status.equals(NotificationStatus.ON) ? NotificationStatus.OFF : NotificationStatus.ON);

        return profile.getBookmarkNotificationStatus();
    }

    @Transactional
    @Override
    public void deleteProfile(UUID memberId) {
        profileRepository.deleteByMemberId(memberId);
        // TODO redis에서 해당 회원의 북마크 내역 삭제
    }

    /**
     * 요청 유저의 ID와 접근하려는 유저(타겟) ID가 같은지 확인
     */
    private void checkValidAccess(UUID accessId, UUID targetId) {
        if (!accessId.equals(targetId)) {
            throw ProfileUnauthorizedException.EXCEPTION;
        }
    }

    /**
     * 유저 ID로 프로필 조회
     */
    private Profile getProfileByMemberId(UUID memberId) {
        return profileRepository.findByMemberId(memberId).orElseThrow(ProfileNotFoundException::new);
    }

}
