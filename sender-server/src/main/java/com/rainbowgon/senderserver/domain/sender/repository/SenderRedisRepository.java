package com.rainbowgon.senderserver.domain.sender.repository;

import com.rainbowgon.senderserver.domain.sender.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface SenderRedisRepository extends CrudRepository<Notification, Long> {
}
