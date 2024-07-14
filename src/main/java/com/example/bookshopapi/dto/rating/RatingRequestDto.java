package com.example.bookshopapi.dto.rating;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingRequestDto {
    private int id;
    private String comment;
    private int ratingLevel;
    private UUID productId;
    private UUID userId;
    private UUID orderId;
}
