package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findById(int id);
}
