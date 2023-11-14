package com.rainbowgon.reservationservice.global.client;

import com.rainbowgon.reservationservice.global.client.dto.input.ThemeBriefInfoInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "search-service")
public interface SearchServiceClient {

    // TODO URL 수정하기
    @GetMapping("/{theme-id}")
    ThemeBriefInfoInDto getThemeBriefInfo(@PathVariable("theme-id") String themeId);

    // TODO URL 수정하기
    @GetMapping("/{theme-id}/{headcount}")
    Integer getTotalPrice(@PathVariable("theme-id") String themeId, @PathVariable Integer headcount);

}