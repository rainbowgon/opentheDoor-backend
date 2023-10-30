package com.rainbowgon.member.domain.profile.service;

import com.rainbowgon.member.domain.member.entity.Member;
import com.rainbowgon.member.domain.profile.entity.Profile;

public interface ProfileService {

    Profile createProfile(Member member, String nickname, String profileImage);
}
