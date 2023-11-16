package com.rainbowgon.reservationservice.domain.timeslot.repository;

import com.rainbowgon.reservationservice.domain.timeslot.entity.TimeLine;
import org.springframework.data.repository.CrudRepository;

public interface TimeLineRedisRepository extends CrudRepository<TimeLine, String> {
}
