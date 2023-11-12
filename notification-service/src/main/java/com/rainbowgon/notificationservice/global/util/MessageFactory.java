package com.rainbowgon.notificationservice.global.util;

import com.rainbowgon.notificationservice.domain.kafka.dto.output.MessageOutDto;
import com.rainbowgon.notificationservice.domain.notification.entity.NotificationType;
import com.rainbowgon.notificationservice.global.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.WaitingInDto;

import java.time.format.DateTimeFormatter;

public class MessageFactory {

    public static MessageOutDto makeBookmarkMessage(BookmarkInDto bookmarkInDto) {

        String title = String.format("%s 예약 오픈 알림", bookmarkInDto.getTitle());
        String body = String.format("북마크 해두신 %s %s 예약이 10분 뒤 %s에 오픈됩니다.",
                                    bookmarkInDto.getVenue(), bookmarkInDto.getTitle(),
                                    bookmarkInDto.getOpenTime().format(DateTimeFormatter.ofPattern("a KK시 mm분")),
                                    bookmarkInDto.getOpenTime());

        return MessageOutDto.from(bookmarkInDto, title, body);
    }

    public static MessageOutDto makeReservationMessage(ReservationInDto reservationInDto) {

        String reservationStatus =
                reservationInDto.getReservationNotificationType() == NotificationType.RESERVATION ?
                        "완료" : "취소";

        String title = String.format("%s 예약 %s 알림", reservationInDto.getTitle(),
                                     reservationStatus);
        String body = String.format("%s %s에 예약하신 %s 예약이 %s되었습니다.",
                                    reservationInDto.getTargetDate().format(DateTimeFormatter.ofPattern(
                                            "yyyy년 MM월 dd일")),
                                    reservationInDto.getTargetTime().format(DateTimeFormatter.ofPattern("a KK시 mm분")),
                                    reservationInDto.getTitle(), reservationStatus);

        return MessageOutDto.from(reservationInDto, title, body);
    }

    public static MessageOutDto makeWaitingMessage(WaitingInDto waitingInDto) {

        String title = String.format("%s 예약 대기 알림", waitingInDto.getTitle());
        String body = String.format("눈여겨보신 %s %s %s 테마가 지금 예약 가능합니다! 지금 바로 예약해주세요!",
                                    waitingInDto.getTitle(),
                                    waitingInDto.getTargetDate().format(DateTimeFormatter.ofPattern(
                                            "yyyy년 MM월 dd일")),
                                    waitingInDto.getTargetTime().format(DateTimeFormatter.ofPattern("a KK시 mm분")));

        return MessageOutDto.from(waitingInDto, title, body);
    }
}
