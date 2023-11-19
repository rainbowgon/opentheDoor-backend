package com.rainbowgon.searchservice.global.client;

import com.rainbowgon.searchservice.global.client.dto.input.ReservationInDtoList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservation-service")
public interface ReservationServiceClient {

    @GetMapping("/clients/timelines/{theme-id}")
    ReservationInDtoList getTimeslot(@PathVariable("theme-id") String themeId);

}
