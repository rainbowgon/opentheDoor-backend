package com.rainbowgon.reservationservice.global.client.dto.input;

import lombok.Getter;

import java.util.List;

@Getter
public class ThemeBreifInfoInDto {

    String poster;
    String title;
    String venue;
    String location;
    List<String> genre;
    List<PriceVO> priceList;
    String siteToS;
    String venueToS;

}
