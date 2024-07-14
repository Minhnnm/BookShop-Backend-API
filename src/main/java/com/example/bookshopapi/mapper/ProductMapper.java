package com.example.bookshopapi.mapper;

import com.example.bookshopapi.dto.product.ProductDto;
import com.example.bookshopapi.dto.product.ProductRequestDto;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.mapper.decorator.ProductDecorator;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
@DecoratedWith(ProductDecorator.class)
public interface ProductMapper extends EntityMapper<Product, ProductDto> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product toEntity(ProductRequestDto productRequest);
}
