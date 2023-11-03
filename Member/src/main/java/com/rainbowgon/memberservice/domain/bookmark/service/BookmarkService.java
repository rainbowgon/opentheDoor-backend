package com.rainbowgon.member.domain.bookmark.service;

import com.rainbowgon.member.domain.bookmark.dto.request.BookmarkUpdateReqDto;
import com.rainbowgon.member.domain.bookmark.dto.response.BookmarkDetailResDto;
import com.rainbowgon.member.domain.bookmark.dto.response.BookmarkSimpleResDto;
import com.rainbowgon.member.global.entity.NotificationStatus;

import java.util.UUID;

public interface BookmarkService {

    void updateBookmarkList(UUID memberId, BookmarkUpdateReqDto bookmarkUpdateReqDto);

    BookmarkSimpleResDto selectSimpleBookmarkList(UUID memberId);

    BookmarkDetailResDto selectDetailBookmarkList(UUID memberId);

    NotificationStatus updateNotificationStatus(UUID memberId, Long bookmarkId);
}
