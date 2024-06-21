package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.repository.ProductRepository;
import com.example.bookshopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public List<Product> getProductsNew() {
        return productRepository.findTop20ByOrderByIdDesc();
    }

    public List<Product> getProductsHot() {
        return productRepository.findTop20ByOrderByQuantitySoldDesc();
    }

    public List<Product> getProductsBanner() {
        return productRepository.findTop5ByBannerIsNotNullOrderByIdDesc();
    }

    public List<Product> getBookByQuantitySold() {
        return productRepository.findTop5ByOrderByQuantitySoldDesc();
    }

    @Override
    public Page<Product> findProductsByCategory(int categoryId, int descriptionLength, int page, int limit) {
        Pageable pageable= PageRequest.of(page, limit);
        return productRepository.findAllByCategoryIdAndDescriptionLengthGreaterThanEqual(categoryId, descriptionLength, pageable);
    }
}
