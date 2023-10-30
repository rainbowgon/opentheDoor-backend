package com.rainbowgon.member.domain.profile.service;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.profile.dto.response.ProfileCreateResDto;

public interface ProfileService {

    ProfileCreateResDto createProfile(Member member, String nickname, String profileImage);

}
