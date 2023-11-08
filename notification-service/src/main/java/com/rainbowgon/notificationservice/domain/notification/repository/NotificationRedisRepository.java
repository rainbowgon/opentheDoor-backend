package com.rainbowgon.notificationservice.domain.notification.repository;

import com.rainbowgon.notificationservice.domain.notification.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface NotificationRedisRepository extends CrudRepository<Notification, Long> {

    Set<Optional<Object>> findByProfileId(Long profileId);
}
