package com.rainbowgon.member.domain.profile.repository;

import com.rainbowgon.member.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
