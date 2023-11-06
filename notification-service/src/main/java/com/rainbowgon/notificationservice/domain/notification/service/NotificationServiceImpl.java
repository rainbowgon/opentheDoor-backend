package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.kafka.dto.out.MessageOutDto;
import com.rainbowgon.notificationservice.domain.kafka.service.KafkaProducer;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.BookmarkInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.ReservationInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.WaitingInDto;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.global.util.MessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaProducer kafkaProducer;

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

        for (BookmarkInDto bookmarkInDto : bookmarkInDtoList) {
            kafkaProducer.sendMessage(MessageFactory.makeBookmarkMessage(bookmarkInDto));
        }
    }

    @Override
    public void sendReservationMessage(List<ReservationInDto> reservationReqDtoList) {

        for (ReservationInDto reservationInDto : reservationReqDtoList) {
            kafkaProducer.sendMessage(MessageFactory.makeReservationMessage(reservationInDto));
        }
    }

    @Override
    public void sendWaitingMessage(List<WaitingInDto> waitingInDtoList) {
        for (WaitingInDto waitingInDto : waitingInDtoList) {

            String title = String.format("%s 예약 대기 알림", waitingInDto.getThemeName());
            String body = String.format("눈여겨보신 %s %s %s 테마가 지금 예약 가능합니다! 지금 바로 예약해주세요!",
                                        waitingInDto.getThemeName(),
                                        waitingInDto.getReservationDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                                        waitingInDto.getReservationTime().format(DateTimeFormatter.ofPattern("a KK시 mm분")));

            MessageOutDto messageOutDto = MessageOutDto.from(waitingInDto, title, body);
            kafkaProducer.sendMessage(MessageFactory.makeWaitingMessage(waitingInDto));
        }
    }
}