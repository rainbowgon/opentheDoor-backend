package com.rainbowgon.memberservice.domain.bookmark.service;

import com.rainbowgon.memberservice.domain.bookmark.dto.request.BookmarkUpdateReqDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkDetailResDto;
import com.rainbowgon.memberservice.domain.bookmark.dto.response.BookmarkSimpleResDto;

import java.util.List;
import java.util.UUID;

public interface BookmarkService {

    void updateBookmarkList(UUID memberId, BookmarkUpdateReqDto bookmarkUpdateReqDto);

    List<BookmarkSimpleResDto> selectSimpleBookmarkList(UUID memberId);

    List<BookmarkDetailResDto> selectDetailBookmarkList(UUID memberId);

    String updateNotificationStatus(UUID memberId, String themeId);

    void deleteBookmark(UUID memberId);
}
