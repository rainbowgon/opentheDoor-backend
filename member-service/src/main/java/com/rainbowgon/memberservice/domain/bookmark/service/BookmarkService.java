package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.dto.request.BookmarkUpdateReqDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkDetailResDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkSimpleResDto;

import java.util.UUID;

public interface BookmarkService {

    void updateBookmarkList(UUID memberId, BookmarkUpdateReqDto bookmarkUpdateReqDto);

    BookmarkSimpleResDto selectSimpleBookmarkList(UUID memberId);

    BookmarkDetailResDto selectDetailBookmarkList(UUID memberId);

    String updateNotificationStatus(UUID memberId, String themeId);
}
