package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.kafka.dto.out.MessageOutDto;
import com.rainbowgon.notificationservice.domain.kafka.service.KafkaProducer;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.BookmarkInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.ReservationInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.WaitingInDto;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.domain.notification.entity.Type;
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
    public void makeBookmarkMessage(List<BookmarkInDto> bookmarkInDtoList) {
        for (BookmarkInDto bookmarkInDto : bookmarkInDtoList) {
            String title = String.format("%s 예약 오픈 알림", bookmarkInDto.getThemeName());
            String body = String.format("북마크 해두신 %s %s 예약이 10분 뒤 %s에 오픈됩니다.",
                                        bookmarkInDto.getVenueName(), bookmarkInDto.getThemeName(),
                                        bookmarkInDto.getOpenTime().format(DateTimeFormatter.ofPattern("a KK시 mm분")),
                                        bookmarkInDto.getOpenTime());

            MessageOutDto messageOutDto = MessageOutDto.from(bookmarkInDto, title, body);
            kafkaProducer.sendMessage(messageOutDto);
        }
    }

    @Override
    public void makeReservationMessage(List<ReservationInDto> reservationReqDtoList) {
        for (ReservationInDto reservationInDto : reservationReqDtoList) {
            String reservationStatus = reservationInDto.getReservationType() == Type.RESERVATION ?
                    "완료" : "취소";

            String title = String.format("%s 예약 %s 알림", reservationInDto.getThemeName(),
                                         reservationStatus);
            String body = String.format("%s %s에 예약하신 %s 예약이 %s되었습니다.",
                                        reservationInDto.getReservationDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                                        reservationInDto.getReservationTime().format(DateTimeFormatter.ofPattern("a KK시 mm분")),
                                        reservationInDto.getThemeName(), reservationStatus);

            MessageOutDto messageOutDto = MessageOutDto.from(reservationInDto, title, body, Type.RESERVATION);
            kafkaProducer.sendMessage(messageOutDto);
        }
    }

    @Override
    public void makeWaitingMessage(List<WaitingInDto> waitingInDtoList) {
        for (WaitingInDto waitingInDto : waitingInDtoList) {

            String title = String.format("%s 예약 대기 알림", waitingInDto.getThemeName());
            String body = String.format("눈여겨보신 %s %s %s 테마가 지금 예약 가능합니다! 지금 바로 예약해주세요!",
                                        waitingInDto.getThemeName(),
                                        waitingInDto.getReservationDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                                        waitingInDto.getReservationTime().format(DateTimeFormatter.ofPattern("a KK시 mm분")));

            MessageOutDto messageOutDto = MessageOutDto.from(waitingInDto, title, body);
            kafkaProducer.sendMessage(messageOutDto);
        }
    }
}