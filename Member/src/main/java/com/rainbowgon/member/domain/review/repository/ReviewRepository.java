package com.rainbowgon.member.domain.review.repository;

import com.rainbowgon.member.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
