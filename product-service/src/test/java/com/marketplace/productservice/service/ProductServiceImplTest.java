package com.marketplace.productservice.service;

import com.marketplace.common.model.Role;
import com.marketplace.productservice.dto.ProductDto;
import com.marketplace.productservice.dto.ProductResponseDto;
import com.marketplace.productservice.dto.ProductUpdateDto;
import com.marketplace.productservice.mapper.ProductMapper;
import com.marketplace.productservice.model.Product;
import com.marketplace.productservice.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;
    private ProductResponseDto productResponseDto;
    private ProductUpdateDto productUpdateDto;

    @BeforeEach
    public void setUp() {
        product = Product.builder().id(1L).name("Phone").description("Censor phone")
                .price(500.)
                .categoryId(1)
                .sellerId(10)
                .build();

        productDto = new ProductDto("Phone", "Censor phone", 500.0, 1, 10);
        productUpdateDto = new ProductUpdateDto("Phone", "Censor phone", 500.0, 1, 10);
        productResponseDto = new ProductResponseDto("Phone", "Censor phone", 500.0, 1, 10);

    }

    @Test
    public void getProducts_shouldReturnListOfProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toProductResponseDto(product)).thenReturn(productResponseDto);

        List<ProductResponseDto> result = productService.getProducts();

        assertEquals(1, result.size());
        assertEquals("Censor phone", result.get(0).getDescription());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toProductResponseDto(product);
    }

    @Test
    public void getProduct_shouldReturnProductWhenProductExists(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toProductResponseDto(product)).thenReturn(productResponseDto);

        assertEquals(500.0, productService.getProduct(1L).getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toProductResponseDto(product);
    }

    @Test
    public void getProduct_shouldThrowExceptionWhenProductDoesNotExist(){
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        var ex =  assertThrows(ResponseStatusException.class, () -> productService.getProduct(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getMessage().contains("product not found"));
        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(productMapper, Mockito.never()).toProductResponseDto(Mockito.any());
    }

    @Test
    public void createProduct_shouldCreateWhenAllowed(){
        when(productMapper.toProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProductResponseDto(product)).thenReturn(productResponseDto);

        var result = productService.createProduct(productDto, Role.ADMIN);

        assertEquals("Phone", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void createProduct_shouldThrowExceptionWithUserRole(){
        when(productMapper.toProduct(productDto)).thenReturn(product);

        var ex = assertThrows(ResponseStatusException.class, () -> productService.createProduct(productDto, Role.USER));

        assertTrue(ex.getMessage().contains("no permission"));
        verify(productRepository, never()).save(any());
    }

    @Test
    public void updateProduct_shouldUpdateProductWhenAllowed(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.updateProductFromDto(product, productUpdateDto)).thenReturn(product);
        when(productMapper.toProductResponseDto(product)).thenReturn(productResponseDto);
        when(productRepository.save(product)).thenReturn(product);

        var result = productService.updateProduct(1L, productUpdateDto, Role.ADMIN);
        assertEquals("Phone", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void updateProduct_shouldThrowExceptionWhenProductDoesNotExist(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        var ex = assertThrows(ResponseStatusException.class, () -> productService.updateProduct(1L, productUpdateDto, Role.ADMIN));
        assertTrue(ex.getMessage().contains("product not found"));
        verify(productRepository, never()).save(any());
    }

    @Test
    public void updateProduct_shouldThrowExceptionWithUserRole(){
        var ex = assertThrows(ResponseStatusException.class, () -> productService.updateProduct(1L, productUpdateDto, Role.USER));

        assertTrue(ex.getMessage().contains("no permission"));
        verify(productRepository, never()).save(any());
    }

    @Test
    public void deleteProduct_shouldDeleteProductWhenAllowed(){
        productService.deleteProduct(1L, Role.ADMIN);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteProduct_shouldThrowExceptionWithUserRole(){
        var ex = assertThrows(ResponseStatusException.class, () -> productService.deleteProduct(1L, Role.USER));
        assertTrue(ex.getMessage().contains("no permission"));
        verify(productRepository, never()).deleteById(1L);
    }
}
