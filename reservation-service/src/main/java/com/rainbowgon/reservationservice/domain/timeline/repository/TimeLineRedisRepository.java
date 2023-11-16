package com.rainbowgon.reservationservice.domain.timeline.repository;

import com.rainbowgon.reservationservice.domain.timeline.entity.TimeLine;
import org.springframework.data.repository.CrudRepository;

public interface TimeLineRedisRepository extends CrudRepository<TimeLine, String> {
}
