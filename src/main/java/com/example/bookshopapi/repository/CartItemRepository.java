package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByProductIdAndCartUserId(int productId, int userId);
}
