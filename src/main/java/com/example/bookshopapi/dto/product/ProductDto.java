package com.example.bookshopapi.dto.product;

import com.example.bookshopapi.entity.Author;
import com.example.bookshopapi.entity.Category;
import com.example.bookshopapi.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal discounted_price;

    private int quantity;

    private int quantitySold;

    private String image;

    private String thumbnail;

    private String authorName;

    private int authorId;

    private String supplierName;

    private int supplierId;

    private String categoryName;

    private int categoryId;

    private String banner;
}
