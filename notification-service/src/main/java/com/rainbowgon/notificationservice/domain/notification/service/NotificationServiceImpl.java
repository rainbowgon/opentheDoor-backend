package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.kafka.service.KafkaProducer;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.domain.notification.entity.ViewStatus;
import com.rainbowgon.notificationservice.domain.notification.repository.NotificationRedisRepository;
import com.rainbowgon.notificationservice.global.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.WaitingInDto;
import com.rainbowgon.notificationservice.global.util.KeyManager;
import com.rainbowgon.notificationservice.global.util.MessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaProducer kafkaProducer;
    private final NotificationRedisRepository notificationRedisRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public List<NotificationListResDto> selectNotificationList(Long memberId) {

        return notificationRedisRepository.findAllByMemberId(memberId).stream()
                .map(notification -> NotificationListResDto.from(notification)).collect(Collectors.toList());
    }

    @Override
    public void checkOneNotification(Long notificationId) {

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String key = KeyManager.makeRedisKey("notification:", notificationId);
        Map<String, String> map = hashOperations.entries(key);

        hashOperations.put(key, "viewStatus", String.valueOf(ViewStatus.VIEWED));
    }

    @Override
    public void checkAllNotification(Long memberId) {

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        String key = KeyManager.makeRedisKey("notification:memberId:", memberId);
        Set<String> set = setOperations.members(key);

        set.stream().forEach(notificationId -> hashOperations.put("notification:" + notificationId,
                                                                  "viewStatus",
                                                                  String.valueOf(ViewStatus.VIEWED)));
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