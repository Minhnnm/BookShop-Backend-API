package com.example.bookshopapi.mapper;

import com.example.bookshopapi.dto.product.ProductDto;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.mapper.decorator.ProductDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(ProductDecorator.class)
public interface ProductMapper extends EntityMapper<Product, ProductDto> {
}
