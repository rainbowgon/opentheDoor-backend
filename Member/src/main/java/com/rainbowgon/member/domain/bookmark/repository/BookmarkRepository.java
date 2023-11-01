package com.rainbowgon.member.domain.bookmark.repository;

import com.rainbowgon.member.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByProfileId(Long profileId);

    Optional<Bookmark> findByProfileIdAndThemeId(Long profileId, Long themeId);
}
