package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.kafka.service.KafkaProducer;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.domain.notification.entity.Notification;
import com.rainbowgon.notificationservice.domain.notification.repository.NotificationRedisRepository;
import com.rainbowgon.notificationservice.global.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.WaitingInDto;
import com.rainbowgon.notificationservice.global.util.MessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaProducer kafkaProducer;
    private final RedisTemplate<String, Notification> redisTemplate;
    private final NotificationRedisRepository notificationRedisRepository;

    @Override
    public List<NotificationListResDto> selectNotificationList(Long profileId) {

        SetOperations<String, Notification> setOperations = redisTemplate.opsForSet();
        String key = "notification:profileId:" + profileId;
        Set<Notification> set = setOperations.members(key);


        return notificationRedisRepository.findAllByProfileId(profileId).stream()
                .map(notification -> NotificationListResDto.from(notification)).collect(Collectors.toList());
    }

    @Override
    public void checkOneNotification(Long notificationId) {

    }

    @Override
    public void checkAllNotification(Long profileId) {

    }

    @Override
    public void sendBookmarkMessage(List<BookmarkInDto> bookmarkInDtoList) {

        bookmarkInDtoList.stream()
                .forEach(bookmarkInDto -> kafkaProducer.sendMessage(MessageFactory.makeBookmarkMessage(bookmarkInDto)));
    }

    @Override
    public void sendReservationMessage(List<ReservationInDto> reservationReqDtoList) {

        reservationReqDtoList.stream()
                .forEach(reservationInDto -> kafkaProducer.sendMessage(MessageFactory.makeReservationMessage(reservationInDto)));
    }

    @Override
    public void sendWaitingMessage(List<WaitingInDto> waitingInDtoList) {

        waitingInDtoList.stream()
                .forEach(waitingInDto -> kafkaProducer.sendMessage(MessageFactory.makeWaitingMessage(waitingInDto)));
    }
}