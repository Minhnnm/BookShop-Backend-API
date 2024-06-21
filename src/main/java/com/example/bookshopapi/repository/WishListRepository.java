package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
}
