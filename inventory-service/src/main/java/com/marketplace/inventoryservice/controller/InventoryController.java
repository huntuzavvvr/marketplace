package com.marketplace.inventoryservice.controller;

import com.marketplace.inventoryservice.dto.InventoryDto;
import com.marketplace.inventoryservice.dto.InventoryResponseDto;
import com.marketplace.inventoryservice.service.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryServiceImpl inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponseDto> addToInventory(@RequestBody InventoryDto inventoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addToInventory(inventoryDto));
    }
}
