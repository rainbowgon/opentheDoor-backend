package com.rainbowgon.member.domain.profile.service;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.profile.entity.Profile;
import com.rainbowgon.member.domain.profile.repository.ProfileRepository;
import com.rainbowgon.member.global.error.exception.ProfileNicknameDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile createProfile(Member member, String nickname, String profileImage) {

        // 닉네임 중복체크
        if (profileRepository.existsByNickname(nickname)) {
            throw ProfileNicknameDuplicationException.EXCEPTION;
        }

        // 프로필 생성
        return profileRepository.save(
                Profile.builder()
                        .member(member)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .build());
    }
}
