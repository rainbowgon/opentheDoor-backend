package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.output.EmptyTimeSlotNotificationOutDto;
import com.rainbowgon.reservationservice.global.client.dto.output.NotificationOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "notification-service", path = "/clients/notifications")
public interface NotificationServiceClient {

    @PostMapping("/reservation")
    String notifyReservationSuccess(
            @RequestBody NotificationOutDto successNotificationOutDto);

    @PostMapping("/waiting")
    String notifyEmptyTimeSlot(
            @RequestBody List<EmptyTimeSlotNotificationOutDto> emptyTimeSlotNotificationOutDtoList);
}
