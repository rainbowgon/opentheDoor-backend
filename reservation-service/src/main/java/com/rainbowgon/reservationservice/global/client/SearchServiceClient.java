package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import com.rainbowgon.reservationservice.global.client.dto.input.ThemeOriginalInfoInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "search-service")
@RequestMapping("/clients/themes")
public interface SearchServiceClient {

    @GetMapping("/brief/{theme-id}")
    ThemeBriefInfoInDto getThemeBriefInfo(@PathVariable("theme-id") String themeId);

    @GetMapping("/totalprice/{theme-id}/{headcount}")
    Integer getTotalPrice(@PathVariable("theme-id") String themeId, @PathVariable Integer headcount);

    @GetMapping("/original/{theme-id}")
    ThemeOriginalInfoInDto getOriginalInfo(@PathVariable("theme-id") String themeId);
}