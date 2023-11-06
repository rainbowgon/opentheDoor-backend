package com.rainbowgon.notificationservice.domain.notification.service;

import com.rainbowgon.notificationservice.domain.notification.client.dto.in.BookmarkInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.ReservationInDto;
import com.rainbowgon.notificationservice.domain.notification.client.dto.in.WaitingInDto;
import com.rainbowgon.notificationservice.domain.notification.dto.response.NotificationListResDto;

import java.util.List;

public interface NotificationService {

    List<NotificationListResDto> selectNotificationList(Long profileId);

    void checkOneNotification(Long notificationId);

    void checkAllNotification(Long profileId);

    void makeBookmarkMessage(List<BookmarkInDto> bookmarkInDtoList);

    void makeReservationMessage(List<ReservationInDto> reservationInDtoList);

    void makeWaitingMessage(List<WaitingInDto> waitingInDtoList);

}