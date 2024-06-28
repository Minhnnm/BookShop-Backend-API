package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supplier, Integer> {
}
