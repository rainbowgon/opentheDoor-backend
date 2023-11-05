package com.rainbowgon.memberservice.domain.bookmark.repository;

import com.rainbowgon.memberservice.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByProfileId(Long profileId);

    Optional<Bookmark> findByProfileIdAndThemeId(Long profileId, String themeId);
}
