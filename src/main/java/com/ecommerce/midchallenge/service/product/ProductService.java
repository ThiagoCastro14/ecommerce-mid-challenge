package com.ecommerce.midchallenge.service.product;

import com.ecommerce.midchallenge.domain.product.Product;
import com.ecommerce.midchallenge.dto.product.ProductDTO;
import com.ecommerce.midchallenge.mapper.product.ProductMapper;
import com.ecommerce.midchallenge.repository.product.ProductRepository;
import com.ecommerce.midchallenge.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    public ProductDTO create(ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        product = repository.save(product);
        return mapper.toDto(product);
    }

    public List<ProductDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public ProductDTO findById(String id) {
        Product product = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return mapper.toDto(product);
    }

    public ProductDTO update(String id, ProductDTO dto) {
        Product existing = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setCategory(dto.getCategory());
        existing.setStockQuantity(dto.getStockQuantity());

        existing = repository.save(existing);
        return mapper.toDto(existing);
    }

    public void delete(String id) {
        Product product = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        repository.delete(product);
    }
}
