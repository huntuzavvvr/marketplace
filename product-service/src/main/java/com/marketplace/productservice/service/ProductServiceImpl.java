package com.marketplace.productservice.service;

import com.marketplace.common.model.Role;
import com.marketplace.productservice.dto.ProductDto;
import com.marketplace.productservice.dto.ProductResponseDto;
import com.marketplace.productservice.mapper.ProductMapper;
import com.marketplace.productservice.model.Product;
import com.marketplace.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductResponseDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        return productRepository.findById(id).map(productMapper::toProductResponseDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
    }

    @Override
    public ProductResponseDto createProduct(ProductDto productDto, Role role) {
        Product product = productMapper.toProduct(productDto);
        if (role == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "no permission");
        }
        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductDto productDto, Role role) {
        if (role == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "no permission");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        productMapper.updateProductFromDto(product, productDto);
        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id, Role role){
        if (role == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "no permission");
        }
        productRepository.deleteById(id);
    }
}
