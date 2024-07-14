package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Page<Rating> findAllByProductIdOrderByCreateTimeDesc(UUID productId, Pageable pageable);

    List<Rating> findAllByProductId(UUID productId);

    Rating save(Rating rating);

    Page<Rating> findAllByUserIdOrderByCreateTimeDesc(UUID userId, Pageable pageable);
}
