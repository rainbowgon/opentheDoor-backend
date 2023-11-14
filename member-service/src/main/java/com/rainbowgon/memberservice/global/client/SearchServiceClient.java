package com.rainbowgon.memberservice.global.client;

import com.rainbowgon.memberservice.global.client.dto.input.ThemeDetailInDto;
import com.rainbowgon.memberservice.global.client.dto.input.ThemeSimpleInDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "search-service")
public interface SearchServiceClient {

    @PostMapping("/clients/themes/detail-lists")
    List<ThemeDetailInDto> getBookmarkThemeDetailInfo(@RequestBody List<String> themeIdList);

    @PostMapping("/clients/themes/simple-lists")
    List<ThemeSimpleInDto> getBookmarkThemeSimpleInfo(@RequestBody List<String> themeIdList);
}
