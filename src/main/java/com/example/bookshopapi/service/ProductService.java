package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.product.ProductDto;
import com.example.bookshopapi.dto.product.ProductRequestDto;
import com.example.bookshopapi.entity.Author;
import com.example.bookshopapi.entity.Category;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.entity.Supplier;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> searchProduct(int page, int limit, String query, String sortBy, String sortDir, Integer categoryId,
                                   Integer authorId, Integer supplierId);
    List<ProductDto> findByCategory(Integer categoryId);
    List<ProductDto> findBySupplier(Integer supplierId);
    List<ProductDto> findByAuthor(Integer authorId);
    ProductDto findById(Integer id);

    MessageDto create(ProductRequestDto product);
//    Optional<Product> findById(int productId);
//
//    Optional<Product> findByName(String productName);
//
//    List<Product> getProductsNew();
//
//    List<Product> getProductsHot();
//
//    List<Product> getProductsBanner();
//
//    List<Product> getBookByQuantitySold();
//
//    Page<Product> findProductsByCategory(int categoryId, int descriptionLength, int page, int limit);

}
