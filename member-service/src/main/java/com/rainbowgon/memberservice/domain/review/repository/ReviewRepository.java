package com.rainbowgon.memberservice.domain.review.repository;

import com.rainbowgon.memberservice.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findTopByThemeIdOrderByCreatedAtDesc(String themeId);

    Optional<Review> findByThemeIdAndMemberId(String themeId, UUID memberId);

    List<Review> findAllByThemeId(String themeId);

    List<Review> findAllByMemberId(UUID memberId);

}
