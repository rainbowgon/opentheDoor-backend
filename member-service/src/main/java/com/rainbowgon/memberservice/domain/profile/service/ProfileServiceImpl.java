package com.rainbowgon.memberservice.domain.profile.service;

import com.rainbowgon.memberservice.domain.member.dto.MemberDto;
import com.rainbowgon.memberservice.domain.member.dto.response.LoginResDto;
import com.rainbowgon.memberservice.domain.member.entity.Member;
import com.rainbowgon.memberservice.domain.profile.dto.response.ProfileSimpleResDto;
import com.rainbowgon.memberservice.domain.profile.entity.Profile;
import com.rainbowgon.memberservice.domain.profile.repository.ProfileRepository;
import com.rainbowgon.memberservice.global.entity.NotificationStatus;
import com.rainbowgon.memberservice.global.error.exception.AwsS3ErrorException;
import com.rainbowgon.memberservice.global.error.exception.ProfileNotFoundException;
import com.rainbowgon.memberservice.global.error.exception.ProfileUnauthorizedException;
import com.rainbowgon.memberservice.global.jwt.JwtTokenProvider;
import com.rainbowgon.memberservice.global.redis.dto.Token;
import com.rainbowgon.memberservice.global.redis.repository.TokenRedisRepository;
import com.rainbowgon.memberservice.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileRepository profileRepository;
    private final TokenRedisRepository tokenRedisRepository;
    private final S3FileService s3FileService;

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private static final String S3_PATH = "profile-image";
    @Value("${cloud.aws.s3.directory}")
    private String directory;


    @Transactional
    @Override
    public LoginResDto createProfile(Member member, String fcmToken, String nickname, String profileImage) {

        // 프로필 이미지 url 자르기
        String profileImageName = null;
        if (profileImage != null) {
            profileImageName = profileImage.substring(profileImage.indexOf(directory));
        }

        // 프로필 생성
        Profile profile = profileRepository.save(
                Profile.builder()
                        .member(member)
                        .nickname(nickname)
                        .profileImage(profileImageName)
                        .build());

        // accessToken, refreshToken 생성
        String accessToken = jwtTokenProvider.generateAccessToken(profile.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(profile.getId());

        // accessToken, refreshToken, fcmToken redis 저장
        Token savedToken = tokenRedisRepository.save(
                Token.builder()
                        .profileId(profile.getId())
                        .memberId(String.valueOf(member.getId()))
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .fcmToken(fcmToken)
                        .expiration(REFRESH_TOKEN_EXPIRE_TIME)
                        .build());

        return LoginResDto.of(
                savedToken.getAccessToken(), savedToken.getRefreshToken(), profile.getNickname(), profileImageName);
    }

    /**
     * 다른 도메인에서 회원 ID로 프로필 데이터 조회
     */
    @Override
    public ProfileSimpleResDto selectProfileByMember(UUID memberId) {

        // 회원 ID로 프로필 객체 가져오기
        Profile profile = getProfileByMemberId(memberId);

        // 프로필 이미지 s3 url로 변경
        String profileImageUrl = s3FileService.getS3Url(profile.getProfileImage());

        return ProfileSimpleResDto.from(profile, profileImageUrl);
    }

    @Transactional
    @Override
    public void updateProfile(UUID memberId, Long profileId, String nickname, MultipartFile profileImage) {

        // 프로필 객체 찾기
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        // 유효한 요청인지 확인
        checkValidAccess(profile.getMember().getId(), memberId);

        // 닉네임 수정
        profile.updateNickname(nickname);

        // 프로필 사진 변경됐다면 수정
        if (profileImage != null) {
            // 수정한 프로필 이미지 s3 업로드
            String fileName = null;
            try {
                fileName = s3FileService.saveFile(S3_PATH, profileImage);
            } catch (IOException e) {
                throw AwsS3ErrorException.EXCEPTION;
            }
            profile.updateProfileImage(fileName);
        } else {
            if (profile.getProfileImage() != null) { // 원래 프로필 사진이 있었다면, 기존 프로필 이미지 삭제
                profile.updateProfileImage(null);
                s3FileService.deleteFile(profile.getProfileImage());
            }
        }
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

        // 현재 상태와 반대 상태로 변경하기
        profile.updateBookmarkNotificationStatus(
                status.equals(NotificationStatus.ON) ? NotificationStatus.OFF : NotificationStatus.ON);

        return profile.getBookmarkNotificationStatus();
    }

    @Transactional
    @Override
    public void deleteProfile(UUID memberId) {

        // 프로필 객체 가져오기
        Profile profile = getProfileByMemberId(memberId);
        log.info("[ProfileServiceImpl] deleteProfile ... profileId = {}", profile.getId());

        // 프로필 이미지 S3에서 삭제
        s3FileService.deleteFile(profile.getProfileImage());

        // 프로필 객체 삭제
        profileRepository.delete(profile);

        // redis token 정보 삭제
        tokenRedisRepository.deleteById(profile.getId());
    }

    /**
     * 프로필 ID로 프로필, 멤버 객체 찾기
     */
    @Override
    public MemberDto selectProfileById(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);
        return MemberDto.from(profile);
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
