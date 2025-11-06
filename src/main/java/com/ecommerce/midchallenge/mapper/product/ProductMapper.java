package com.ecommerce.midchallenge.mapper.product;

import com.ecommerce.midchallenge.domain.product.Product;
import com.ecommerce.midchallenge.dto.product.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    ProductDTO toDto(Product product);
    
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductDTO dto);
}