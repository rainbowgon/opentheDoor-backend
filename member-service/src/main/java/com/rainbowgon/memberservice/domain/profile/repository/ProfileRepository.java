package com.rainbowgon.memberservice.domain.profile.repository;

import com.rainbowgon.memberservice.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberId(UUID memberId);

    void deleteByMemberId(UUID memberId);
}
