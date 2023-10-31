package com.rainbowgon.member.domain.member.repository;

import com.rainbowgon.member.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByPhoneNumber(String phoneNumber);

}
