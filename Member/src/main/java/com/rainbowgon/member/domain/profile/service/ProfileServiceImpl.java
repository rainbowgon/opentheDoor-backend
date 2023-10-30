package com.rainbowgon.member.domain.profile.service;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.member.service.MemberService;
import com.rainbowgon.member.domain.profile.dto.request.ProfileUpdateReqDto;
import com.rainbowgon.member.domain.profile.dto.response.ProfileCreateResDto;
import com.rainbowgon.member.domain.profile.entity.Profile;
import com.rainbowgon.member.domain.profile.repository.ProfileRepository;
import com.rainbowgon.member.global.error.exception.ProfileNotFoundException;
import com.rainbowgon.member.global.error.exception.ProfileUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    private final MemberService memberService;

    @Override
    public ProfileSelectResDto selectProfile(UUID memberId) {

        // 요청 회원의 프로필 조회
        Profile profile = profileRepository.findByMember(memberId).orElseThrow(ProfileNotFoundException::new);

        // 조회한 프로필의 회원과 요청 회원이 같은지 확인
        if (!profile.getMember().getId().equals(memberId)) {
            throw ProfileUnauthorizedException.EXCEPTION;
        }

        return ProfileSelectResDto.from(profile);
    }

    @Override
    public ProfileCreateResDto createProfile(Member member, String nickname, String profileImage) {

        // 닉네임 중복체크
//        if (profileRepository.existsByNickname(nickname)) {
//            throw ProfileNicknameDuplicationException.EXCEPTION;
//        }

        // 프로필 생성
        Profile profile = profileRepository.save(
                Profile.builder()
                        .member(member)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .build());

        return ProfileCreateResDto.from(profile);
    }

    @Override
    public Boolean updateProfile(UUID memberId, ProfileUpdateReqDto profileUpdateReqDto, MultipartFile profileImage) {

        // 유효한 프로필인지 확인
        Profile profile = getOneProfile(profileUpdateReqDto.getProfileId());

        // 유효한 접근인지 확인
        if (!profile.getMember().getId().equals(memberId)) {
            throw ProfileUnauthorizedException.EXCEPTION;
        }

        // 닉네임, 이름, 생일 수정
        profile.setNickname(profileUpdateReqDto.getNickname());
        profile.getMember().setName(profileUpdateReqDto.getName());
        profile.getMember().setBirthDate(profileUpdateReqDto.getBirthDate());

        // 프로필 사진 변경됐다면 수정
        if (profileImage != null) {
            // TODO 수정한 프로필 이미지 s3 업로드
            String profileImageUrl = null;

        }

        // 멤버 전화번호 변경됐다면 수정
        if (profileUpdateReqDto.getPhoneNumber() != null) {
            String phoneNumber = profileUpdateReqDto.getPhoneNumber();
            memberService.checkPhoneNumber(phoneNumber); // 이미 존재하는 전화번호인지 확인
            profile.getMember().setPhoneNumber(phoneNumber);
        }

        return true; // try-catch로 변경하기
    }

    /**
     * 프로필 아이디로 프로필 조회
     */
    private Profile getOneProfile(Long profileId) {
        return profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);
    }
}
