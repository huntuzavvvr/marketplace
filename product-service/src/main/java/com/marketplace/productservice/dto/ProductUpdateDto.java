package com.marketplace.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {
    private String name;

    private String description;

    private Double price;

    private Integer categoryId;

    private Integer sellerId;
}
