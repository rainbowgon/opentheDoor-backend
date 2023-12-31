package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;
import com.rainbowgon.notificationservice.global.client.dto.input.BookmarkInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.ReservationInDto;
import com.rainbowgon.notificationservice.global.client.dto.input.WaitingInDto;

import java.util.List;

public interface NotificationService {

    List<NotificationListResDto> selectNotificationList(String memberId);

    void checkOneNotification(Long notificationId);

    void checkAllNotification(String memberId);

    void sendBookmarkMessage(List<BookmarkInDto> bookmarkInDtoList);

    void sendReservationMessage(ReservationInDto reservationInDto);

    void sendWaitingMessage(List<WaitingInDto> waitingInDtoList);

}