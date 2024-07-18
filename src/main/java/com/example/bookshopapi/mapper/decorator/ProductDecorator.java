package com.example.bookshopapi.mapper.decorator;

import com.example.bookshopapi.dto.product.ProductDto;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductDecorator implements ProductMapper {
    @Autowired
    @Qualifier("delegate")
    private ProductMapper delegate;

    @Override
    public ProductDto toDto(Product entity) {
        ProductDto productDto = delegate.toDto(entity);
        if(entity.getAuthor()!=null){
            productDto.setAuthorId(entity.getAuthor().getId());
            productDto.setAuthorName(entity.getAuthor().getName());
        }
        if(entity.getCategory()!=null){
            productDto.setCategoryId(entity.getCategory().getId());
            productDto.setCategoryName(entity.getCategory().getName());
        }
        if(entity.getSupplier()!=null){
            productDto.setSupplierId(entity.getSupplier().getId());
            productDto.setSupplierName(entity.getSupplier().getName());
        }
        return productDto;
    }

    @Override
    public List<ProductDto> toDtos(List<Product> entities) {
        if (entities == null) {
            return null;
        }
        List<ProductDto> dtos = new ArrayList<>();
        for (Product entity : entities) {
            dtos.add(toDto(entity));
        }
        return dtos;
    }
}
