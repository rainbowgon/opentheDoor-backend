package com.rainbowgon.member.domain.bookmark.repository;

import com.rainbowgon.member.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
