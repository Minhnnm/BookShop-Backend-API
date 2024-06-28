package com.example.bookshopapi.repository;

import com.example.bookshopapi.entity.Author;
import com.example.bookshopapi.entity.Category;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("SELECT p from Product p where (p.name = :query or p.description = :query)" +
            "AND p.category = :category AND p.supplier = :suplly AND p.author = :author")
    Page<Product> searchProduct(@Param("query") String query,
                                @Param("category") Category category,
                                @Param("author") Author author,
                                @Param("supply") Supplier supplier,
                                Pageable pageable);
//    Optional<Product> findByName(String productName);

//    List<Product> findTop20ByOrderByIdDesc();
//
//    List<Product> findTop20ByOrderByQuantitySoldDesc();
//
//    List<Product> findTop5ByBannerIsNotNullOrderByIdDesc();
//
//    List<Product> findTop5ByOrderByQuantitySoldDesc();
//
//    @Query("SELECT p FROM Product p WHERE p.category.id = :category_id AND LENGTH(p.description) >= :description_length")
//    Page<Product> findAllByCategoryIdAndDescriptionLengthGreaterThanEqual(@Param("category_id") int categoryId,
//                                                                          @Param("description_length") int descriptionLength,
//                                                                          Pageable pageable);
//

}
