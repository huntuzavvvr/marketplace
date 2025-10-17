package com.marketplace.orderservice.controller;

import com.marketplace.orderservice.dto.OrderDto;
import com.marketplace.orderservice.dto.OrderResponseDto;
import com.marketplace.orderservice.mapper.OrderMapper;
import com.marketplace.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @PostMapping()
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderDto orderDto,
                                                        @RequestHeader Map<String, String> headers){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id, OrderDto orderDto){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(id, orderDto));
    }
}
