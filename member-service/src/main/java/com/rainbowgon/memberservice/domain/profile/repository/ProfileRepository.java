package com.rainbowgon.memberservice.domain.profile.repository;

import com.rainbowgon.memberservice.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberId(UUID memberId);

    void deleteByMemberId(UUID memberId);

    @Query("SELECT  FROM Profile p JOIN FETCH Member m WHERE p.id = :profileId")
    String findFcmTokenById(Long profileId);
}
