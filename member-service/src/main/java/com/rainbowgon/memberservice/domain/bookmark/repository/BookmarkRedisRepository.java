package com.rainbowgon.memberservice.domain.bookmark.repository;

import com.rainbowgon.memberservice.domain.bookmark.entity.BookmarkNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRedisRepository extends CrudRepository<BookmarkNotification, String> {

    void deleteByBookmarkId(Long bookmarkId);
}
