package com.marketplace.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private Double price;

    private Integer categoryId;

    private Integer sellerId;
}
