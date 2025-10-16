package com.marketplace.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.common.model.Role;
import com.marketplace.productservice.dto.ProductDto;
import com.marketplace.productservice.dto.ProductResponseDto;
import com.marketplace.productservice.dto.ProductUpdateDto;
import com.marketplace.productservice.model.Product;
import com.marketplace.productservice.service.ProductService;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductServiceController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private ProductResponseDto productResponseDto;
    private ProductDto productDto;
    private ProductUpdateDto productUpdateDto;

    @BeforeEach
    public void hui(){
        productResponseDto = new ProductResponseDto("Iphone", "cool", 999.0, 1, 10);

    }

    @Test
    public void getProducts_shouldReturnListOfProducts() throws Exception {
        when(productService.getProducts()).thenReturn(List.of(productResponseDto));
        mockMvc.perform(get("/api/product/products"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Iphone"));

    }

    @Test
    public void getProduct_shouldReturnProduct() throws Exception {
        when(productService.getProduct(1L)).thenReturn(productResponseDto);
        mockMvc.perform(get("/api/product/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Iphone"));
    }

    @Test
    public void createProduct_shouldCreateProductWhenAllowed() throws Exception{
        productDto = new ProductDto("Iphone", "cool", 999.0, 1, 10);
        when(productService.createProduct(productDto, Role.ADMIN)).thenReturn(productResponseDto);
        mockMvc.perform(post("/api/product")
                        .header("X-Auth-Role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Iphone"));
    }

    @Test
    public void createProduct_shouldThrowExceptionWhenForbidden() throws Exception {
        productDto = new ProductDto("Iphone", "cool", 999.0, 1, 10);
        when(productService.createProduct(productDto, Role.USER)).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN));
        mockMvc.perform(post("/api/product")
                        .header("X-Auth-Role", "USER")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateProduct_shouldUpdateProductWhenAllowed() throws Exception {
        productUpdateDto = new ProductUpdateDto("Iphone", "cool", 999.0, 1, 10);
        when(productService.updateProduct(1L, productUpdateDto, Role.ADMIN)).thenReturn(productResponseDto);

        mockMvc.perform(put("/api/product/1")
                        .header("X-Auth-Role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Iphone"));
    }

    @Test
    public void updateProduct_shouldThrowExceptionWhenForbidden() throws Exception {
        productUpdateDto = new ProductUpdateDto("Iphone", "cool", 999.0, 1, 10);
        when(productService.updateProduct(1L, productUpdateDto, Role.USER)).thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN));
        mockMvc.perform(put("/api/product/1")
                    .header("X-Auth-Role", "USER")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(productUpdateDto)))
                .andExpect(status().isForbidden());
    }


    @Test
    public void createProduct_shouldThrowExceptionWhenBodyNotValid() throws Exception {
        productDto = new ProductDto();
        productDto.setName("Iphone");
        mockMvc.perform(post("/api/product")
                .header("X-Auth-Role", "USER")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDto))).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteProduct_shouldDeleteWhenAllowed() throws Exception{
        mockMvc.perform(delete("/api/product/1")
                        .header("X-Auth-Role", "SELLER"))
                .andExpect(status().isNoContent());
        verify(productService, times(1)).deleteProduct(1L, Role.SELLER);
    }
}
