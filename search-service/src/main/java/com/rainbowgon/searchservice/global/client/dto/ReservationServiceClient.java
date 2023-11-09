package com.rainbowgon.searchservice.global.client.dto;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "reservation-service")
public interface ReservationServiceClient {

//    @GetMapping("/reservations")
}
