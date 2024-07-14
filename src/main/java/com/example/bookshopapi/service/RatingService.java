package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.rating.RatingRequestDto;
import com.example.bookshopapi.entity.Rating;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface RatingService {
    Page<Rating> findAllByProductId(UUID productId, int limit, int page);

    double ratingLevel(UUID productId);

    MessageDto createRating(List<RatingRequestDto> ratingRequests);

    Page<Rating> findRatingByBookId(int limit, int page);
}
