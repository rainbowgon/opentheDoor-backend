package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.output.EmptyTimeSlotNotificationOutDto;
import com.rainbowgon.reservationservice.global.client.dto.output.NotificationOutDto;
import com.rainbowgon.reservationservice.global.response.ResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "notification-service")
@RequestMapping("/clients/notifications")
public interface NotificationServiceClient {

    @PostMapping("/reservation")
    ResponseWrapper<Nullable> notifyReservationSuccess(
            @RequestBody NotificationOutDto successNotificationOutDto);

    @PostMapping("/waiting")
    ResponseWrapper<Nullable> notifyEmptyTheme(
            @RequestBody List<EmptyTimeSlotNotificationOutDto> emptyTimeSlotNotificationOutDtoList);
}
