package com.rainbowgon.searchservice.global.client;

import com.rainbowgon.searchservice.global.client.dto.input.ReservationInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "reservation-service")
public interface ReservationServiceClient {

    @GetMapping("/clients/reservations/{theme-id}")
    List<ReservationInDto> getTimeslot(@PathVariable("theme-id") String themeId);
    
}
