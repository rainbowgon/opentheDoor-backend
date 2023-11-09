package com.rainbowgon.notificationservice.domain.notification.repository;

import com.rainbowgon.notificationservice.domain.notification.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface NotificationRedisRepository extends CrudRepository<Notification, String> {

    Set<String> findByProfileId(Long profileId);
}
