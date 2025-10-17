package com.marketplace.inventoryservice.mapper;

import com.marketplace.inventoryservice.dto.InventoryResponseDto;
import com.marketplace.inventoryservice.model.Inventory;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryResponseDto toInventoryResponseDto(Inventory inventory);
}
