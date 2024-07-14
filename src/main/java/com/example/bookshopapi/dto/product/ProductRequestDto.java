package com.example.bookshopapi.dto.product;

import com.example.bookshopapi.entity.Author;
import com.example.bookshopapi.entity.Category;
import com.example.bookshopapi.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private int quantity;
    private Author author;
    private Supplier supplier;
    private Category category;
    private Boolean isBannerSelected;
}
