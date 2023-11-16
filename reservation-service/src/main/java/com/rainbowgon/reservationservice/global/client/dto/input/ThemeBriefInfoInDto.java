package com.rainbowgon.reservationservice.global.client.dto.input;

import com.rainbowgon.reservationservice.global.vo.PriceVO;
import lombok.Getter;

import java.util.List;

@Getter
public class ThemeBriefInfoInDto {

    String poster;
    String title;
    String venue;
    String location;
    List<String> genre;
    List<PriceVO> priceList;
    String siteToS;
    String venueToS;

}
