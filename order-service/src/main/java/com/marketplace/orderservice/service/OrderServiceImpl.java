package com.marketplace.orderservice.service;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.common.event.OrderItemEvent;
import com.marketplace.orderservice.dto.OrderDto;
import com.marketplace.orderservice.dto.OrderResponseDto;
import com.marketplace.orderservice.mapper.OrderMapper;
import com.marketplace.orderservice.model.Order;
import com.marketplace.orderservice.repository.OrderRepository;
import com.marketplace.orderservice.service.kafka.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final MessageServiceImpl messageService;

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return orderMapper.toDto(orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")));
    }

    @Override
    public void deleteOrder(Long id){
        orderRepository.findById(id);
    }

    @Override
    public OrderResponseDto updateOrder(Long id, OrderDto orderDto){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
//        order.setOrderName(orderDto.getOrderName());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto createOrder(OrderDto orderDto) {
        OrderCreateEvent orderCreateEvent = new OrderCreateEvent();
        orderCreateEvent.setOrderItems(orderDto.getOrderItems().stream().map(a -> new OrderItemEvent(a.getProductId(), a.getQuantity())).collect(Collectors.toList()));
        System.out.println("SHIT TALK");
        Order order = orderMapper.toEntity(orderDto);
        order.setStatus("PENDING");
        order = orderRepository.save(order);
        System.out.println("Order created");
        orderCreateEvent.setOrderId(order.getId());
        messageService.sendMessage("order-created", orderCreateEvent);


        return orderMapper.toDto(order);
    }

    @Override
    public OrderResponseDto getOrdersByUserId(Long userId) {return null;}

    @Transactional
    public void confirmOrder(OrderCreateEvent orderCreateEvent) {
        System.out.println("CONFIRMATION ORDER");
        Order order = orderRepository.findById(orderCreateEvent.getOrderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (order != null) {
            order.setStatus("CONFIRMED");
            orderRepository.save(order);
        }

    }
}
