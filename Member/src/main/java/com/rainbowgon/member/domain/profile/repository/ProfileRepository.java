package com.rainbowgon.member.domain.profile.repository;

import com.rainbowgon.member.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberId(UUID memberId);

    Optional<Profile> deleteByMemberId(UUID memberId);
}
