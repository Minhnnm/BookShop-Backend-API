package com.example.bookshopapi.dto.product;

import com.example.bookshopapi.entity.Author;
import com.example.bookshopapi.entity.Category;
import com.example.bookshopapi.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discounted_price;
    private int quantity;
    private String image;
    private Author author;
    private Supplier supplier;
    private Category category;
    private Boolean isBannerSelected;
    private String fileName;
}
