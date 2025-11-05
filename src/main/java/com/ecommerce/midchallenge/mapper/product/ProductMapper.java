package com.ecommerce.midchallenge.mapper.product;

import com.ecommerce.midchallenge.domain.product.Product;
import com.ecommerce.midchallenge.dto.product.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO dto);
}
