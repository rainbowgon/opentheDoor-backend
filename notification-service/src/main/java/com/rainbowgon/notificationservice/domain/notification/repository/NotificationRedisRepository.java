package com.rainbowgon.notificationservice.domain.notification.repository;

import com.rainbowgon.notificationservice.domain.notification.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRedisRepository extends CrudRepository<Notification, String> {

    List<Notification> findAllByMemberId(String memberId);
}
