package com.rainbowgon.memberservice.domain.bookmark.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkUpdateReqDto {

    List<String> bookmarkThemeIdList;
}
