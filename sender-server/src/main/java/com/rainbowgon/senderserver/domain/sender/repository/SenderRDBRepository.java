package com.rainbowgon.senderserver.domain.sender.repository;

import com.rainbowgon.senderserver.domain.sender.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenderRDBRepository extends JpaRepository<NotificationLog, Long> {
}
