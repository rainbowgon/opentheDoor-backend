package com.rainbowgon.reservationservice.domain.reservation.controller;

import com.rainbowgon.reservationservice.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class ReservationAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService service;

    @Test
    void makeReservation() {
        //given
        BDDMockito.given(service.makeReservation(
        )).

        //when


        //then

    }
}