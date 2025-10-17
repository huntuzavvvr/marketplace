package com.marketplace.inventoryservice.service;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.common.event.OrderItemEvent;
import com.marketplace.inventoryservice.dto.InventoryDto;
import com.marketplace.inventoryservice.dto.InventoryResponseDto;
import com.marketplace.inventoryservice.mapper.InventoryMapper;
import com.marketplace.inventoryservice.model.Inventory;
import com.marketplace.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl {

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    public InventoryResponseDto addToInventory(@RequestBody InventoryDto inventoryDto){
        Inventory inventory = new Inventory();
        inventory.setProductId(inventoryDto.getProductId());
        inventory.setQuantity(inventoryDto.getQuantity());
        return inventoryMapper.toInventoryResponseDto(inventoryRepository.save(inventory));
    }

    public Inventory createOrder(OrderCreateEvent orderCreateEvent) {
        Inventory inventory = new Inventory();
        checkInventoryAvailability(orderCreateEvent.getOrderItems());
        reserveInventory(orderCreateEvent.getOrderItems());

        return inventory;
    }

    public void checkInventoryAvailability(List<OrderItemEvent> orderItems) {
        for (OrderItemEvent orderItem : orderItems) {
            Inventory inventory = inventoryRepository.findByProductId(orderItem.getProductId());
            if (inventory.getQuantity() < orderItem.getQuantity()) {
                throw new RuntimeException("Quantity exceeded");
            }
        }
    }

    public void reserveInventory(List<OrderItemEvent> orderItems) {
        for (OrderItemEvent orderItem : orderItems) {
            Inventory inventory = inventoryRepository.findByProductId(orderItem.getProductId());
            inventory.setQuantity(inventory.getQuantity() - orderItem.getQuantity());
            inventoryRepository.save(inventory);
        }
    }
}
