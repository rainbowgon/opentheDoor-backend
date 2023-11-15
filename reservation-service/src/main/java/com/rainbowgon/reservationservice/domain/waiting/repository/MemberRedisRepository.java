package com.rainbowgon.reservationservice.domain.waiting.repository;

import com.rainbowgon.reservationservice.domain.waiting.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRedisRepository extends CrudRepository<Member, String> {
}
