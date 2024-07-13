package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByProductIdAndCartUserId(int productId, UUID userId);
}
