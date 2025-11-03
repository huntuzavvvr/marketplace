package com.marketplace.inventoryservice;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.common.event.OrderItemEvent;
import com.marketplace.inventoryservice.dto.InventoryDto;
import com.marketplace.inventoryservice.dto.InventoryResponseDto;
import com.marketplace.inventoryservice.mapper.InventoryMapper;
import com.marketplace.inventoryservice.model.Inventory;
import com.marketplace.inventoryservice.repository.InventoryRepository;
import com.marketplace.inventoryservice.service.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private InventoryMapper inventoryMapper;
    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private InventoryDto inventoryDto;
    private OrderCreateEvent orderCreateEvent;
    private Inventory inventory;
    private InventoryResponseDto inventoryResponseDto;

    @BeforeEach
    public void setUp() {
        inventoryDto = new InventoryDto(1L, 1);
        orderCreateEvent = new OrderCreateEvent(1L, List.of(new OrderItemEvent(1L, 1)));
        inventory = new Inventory(1L, 1L, 1);
        inventoryResponseDto = new InventoryResponseDto(1L, 1);
    }

    @Test
    public void addToInventory_shouldReturnResponseDto() {
        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        when(inventoryMapper.toInventoryResponseDto(inventory)).thenReturn(inventoryResponseDto);
    }

//    @Test
//    public void createOrder_shouldReturnInventory
}
