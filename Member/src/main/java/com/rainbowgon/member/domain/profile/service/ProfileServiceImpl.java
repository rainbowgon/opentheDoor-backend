package com.rainbowgon.member.domain.profile.service;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.profile.dto.response.ProfileCreateResDto;
import com.rainbowgon.member.domain.profile.entity.Profile;
import com.rainbowgon.member.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

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

}
