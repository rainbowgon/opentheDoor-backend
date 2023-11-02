package com.rainbowgon.member.domain.review.repository;

import com.rainbowgon.member.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findTopByThemeIdOrderByCreatedAtDesc(Long themeId);

    Optional<Review> findByThemeIdAndProfileId(Long themeId, Long profileId);

    List<Review> findAllByThemeId(Long themeId);

    List<Review> findAllByProfileId(Long profileId);

}
