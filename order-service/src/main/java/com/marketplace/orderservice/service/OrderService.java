package com.marketplace.orderservice.service;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.orderservice.dto.OrderDto;
import com.marketplace.orderservice.dto.OrderResponseDto;

import javax.management.relation.Role;

public interface OrderService {
    public OrderResponseDto createOrder(OrderDto orderDto);

    public OrderResponseDto getOrderById(Long orderId);

    public OrderResponseDto getOrdersByUserId(Long userId);

    public OrderResponseDto updateOrder(Long id, OrderDto orderDto);

    public void deleteOrder(Long orderId);

    public void confirmOrder(OrderCreateEvent orderCreateEvent);
}
