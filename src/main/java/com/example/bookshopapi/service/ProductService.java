package com.example.bookshopapi.service;

import com.example.bookshopapi.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(int productId);

    Optional<Product> findByName(String productName);

    List<Product> getProductsNew();

    List<Product> getProductsHot();

    List<Product> getProductsBanner();

    List<Product> getBookByQuantitySold();

    Page<Product> findProductsByCategory(int categoryId, int descriptionLength, int page, int limit);

}
