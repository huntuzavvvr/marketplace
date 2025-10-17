package com.marketplace.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryDto {
    private Long productId;

    private Integer quantity;
}
