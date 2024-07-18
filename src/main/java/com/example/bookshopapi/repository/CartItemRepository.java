package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByProductIdAndCartUserId(UUID productId, UUID userId);
}
