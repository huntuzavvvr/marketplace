package com.marketplace.productservice.service;

import com.marketplace.common.model.Role;
import com.marketplace.productservice.dto.*;
import com.marketplace.productservice.mapper.ProductMapper;
import com.marketplace.productservice.model.Product;
import com.marketplace.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Cacheable(value = "skufs")
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductResponseDto).collect(Collectors.toList());
    }

    @Cacheable(value = "skufon11s", key = "'page_' + #page + '_' + #size + '_' + #sortBy")
    public PagedResult<ProductResponseDto> getProducts(int page, int size,
                                                String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponseDto> content = productPage.getContent().stream()
                .map(productMapper::toProductResponseDto).collect(Collectors.toList());
        return new PagedResult<>(content, page, size, productPage.getTotalElements(), productPage.getTotalPages());
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
    public ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto, Role role) {
        if (role == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "no permission");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        productMapper.updateProductFromDto(product, productUpdateDto);
        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    @Override
    @CacheEvict(value = "skufon11s")
    public void deleteProduct(Long id, Role role){
        if (role == Role.USER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "no permission");
        }
        productRepository.deleteById(id);
    }

    
}
