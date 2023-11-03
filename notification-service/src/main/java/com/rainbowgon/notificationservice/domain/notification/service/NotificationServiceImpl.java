package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.kafka.service.KafkaProducer;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.BookmarkInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.ReservationInDto;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.domain.notification.entity.Notification;
import com.rainbowgon.notificationservice.domain.notification.entity.Type;
import com.rainbowgon.notificationservice.domain.notification.entity.ViewStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void makeBookmarkMessage(List<BookmarkInDto> bookmarkReqDtoList) {
        for (BookmarkInDto bookmarkReqDto : bookmarkReqDtoList) {
            String title = bookmarkReqDto.getThemeName() + "예약 오픈 알림";
            String content = "북마크 해두신" + bookmarkReqDto.getVenueName() + " " + bookmarkReqDto.getThemeName()
                    + "예약이 10분 뒤" + bookmarkReqDto.getOpenTime() + "에 오픈됩니다.";

            Notification notification = Notification.builder()
                    .profileId(bookmarkReqDto.getProfileId())
                    .title(title)
                    .content(content)
                    .type(Type.BOOKMARK)
                    .isViewed(ViewStatus.NOT_VIEWED)
                    .build();

            kafkaProducer.sendMessage(notification);
        }
    }

    @Override
    public void makeReservationMessage(List<ReservationInDto> reservationReqDtoList) {

    }

    @Override
    public void makeWaitingMessage(List<ReservationInDto> waitingReqDtoList) {

    }
}
