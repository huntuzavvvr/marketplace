package com.marketplace.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryResponseDto {
    private Long productId;
    private Integer quantity;
}
