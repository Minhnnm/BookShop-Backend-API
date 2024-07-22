package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByProductIdAndCartUserId(UUID productId, UUID userId);

    List<CartItem> findCartItemByCartUserId(UUID idUser);

    Page<CartItem> findCartItemByCartUserIdOrderByUpdateOnDesc(UUID idUser, Pageable pageable);
}
