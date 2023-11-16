package com.rainbowgon.reservationservice.domain.waiting.repository;

import com.rainbowgon.reservationservice.domain.waiting.entity.Waiting;
import org.springframework.data.repository.CrudRepository;

public interface WaitingRedisRepository extends CrudRepository<Waiting, String> {
}
