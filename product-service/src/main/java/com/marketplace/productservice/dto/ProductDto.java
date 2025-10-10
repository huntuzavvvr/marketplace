package com.marketplace.productservice.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;

    private String description;

    private Double price;

    private Integer categoryId;

    private Integer sellerId;
}
