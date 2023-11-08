package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.kafka.service.KafkaProducer;
import com.rainbowgon.notificationservice.domain.notification.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.input.WaitingInDto;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.domain.notification.repository.NotificationRedisRepository;
import com.rainbowgon.notificationservice.global.util.MessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaProducer kafkaProducer;
    private final NotificationRedisRepository notificationRedisRepository;
    private final RedisTemplate<String, String> stringRedisTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<NotificationListResDto> selectNotificationList(Long profileId) {
        return null;
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