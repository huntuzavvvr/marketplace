package com.marketplace.productservice.controller;

import com.marketplace.productservice.dto.*;
import com.marketplace.productservice.service.ProductService;
import com.marketplace.productservice.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.marketplace.common.model.Role;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductServiceController {

    private final ProductService productService;

//    @GetMapping("/products")
//    public ResponseEntity<List<ProductResponseDto>> getProducts(){
//        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
//    }

    @GetMapping("/products")
    public ResponseEntity<PagedResult<ProductResponseDto>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "2") int size){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(page, size, "id"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(id));
    }

//    @GetMapping("/categories")
//    public ResponseEntity<CategoryResponseDto> getCategories(){
//        return ResponseEntity.status(HttpStatus.OK).body(productService.getCategories());
//    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductDto product, @RequestHeader("X-Auth-Role") Role role){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product, role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto product, @RequestHeader("X-Auth-Role") Role role){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, product, role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @RequestHeader("X-Auth-Role") Role role){
        productService.deleteProduct(id, role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
