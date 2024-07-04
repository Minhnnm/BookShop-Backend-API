package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findByUserId(int userId);
}
