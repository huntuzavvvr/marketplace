package com.marketplace.productservice.service;

import com.marketplace.common.model.Role;
import com.marketplace.productservice.dto.PagedResult;
import com.marketplace.productservice.dto.ProductDto;
import com.marketplace.productservice.dto.ProductResponseDto;
import com.marketplace.productservice.dto.ProductUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {

    public List<ProductResponseDto> getProducts();

    public PagedResult<ProductResponseDto> getProducts(int page, int size, String sortBy);

    public ProductResponseDto getProduct(Long id);

    public ProductResponseDto createProduct(ProductDto productDto, Role role);

    public ProductResponseDto updateProduct(Long id, ProductUpdateDto productUpdateDto, Role role);

    public void deleteProduct(Long id, Role role);
}
