package com.rainbowgon.searchservice.global.client;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "reservation-service")
public interface ReservationServiceClient {

//    @GetMapping("/reservations")
}
