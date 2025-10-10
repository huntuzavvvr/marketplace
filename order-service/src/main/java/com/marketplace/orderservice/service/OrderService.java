package com.marketplace.orderservice.service;

import com.marketplace.orderservice.dto.OrderDto;
import com.marketplace.orderservice.dto.OrderResponseDto;
import com.marketplace.orderservice.mapper.OrderMapper;
import com.marketplace.orderservice.model.Order;
import com.marketplace.orderservice.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderResponseDto getOrders(Long id) {
        return orderMapper.toDto(orderRepository.findById(id).orElse(null));
    }

    public void deleteOrder(Long id){
        orderRepository.findById(id);
    }

    public OrderResponseDto updateOrder(Long id, OrderDto orderDto){
        Order order = orderRepository.findById(id).orElse(null);
        order.setOrderName(orderDto.getOrderName());
        return orderMapper.toDto(orderRepository.save(order));
    }

    public OrderResponseDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderName(orderDto.getOrderName());
        return orderMapper.toDto(orderRepository.save(order));
    }
}
