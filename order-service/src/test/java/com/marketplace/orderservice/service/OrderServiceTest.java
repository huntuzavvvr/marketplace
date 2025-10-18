package com.marketplace.orderservice.service;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.common.event.OrderItemEvent;
import com.marketplace.orderservice.dto.OrderDto;
import com.marketplace.orderservice.dto.OrderItemDto;
import com.marketplace.orderservice.dto.OrderResponseDto;
import com.marketplace.orderservice.mapper.OrderMapper;
import com.marketplace.orderservice.model.Order;
import com.marketplace.orderservice.model.OrderItem;
import com.marketplace.orderservice.repository.OrderRepository;
import com.marketplace.orderservice.service.kafka.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDto orderDto;
    private Order order;
    private OrderItem orderItem;
    private OrderResponseDto orderResponseDto;
    private OrderCreateEvent orderCreateEvent;
    private OrderItemEvent orderItemEvent;

    @Mock
    private MessageServiceImpl messageService;

    @BeforeEach
    public void setUp(){
        orderDto = new OrderDto(1L, List.of(new OrderItemDto(1L, 1)));
        orderItem = new OrderItem();
        orderItem.setProductId(1L);
        orderItem.setQuantity(1);
        orderItem.setId(1L);
        order = new Order(1L, "PENDING", 1L, List.of(orderItem));
        orderResponseDto = new OrderResponseDto("phone");
        orderCreateEvent = new OrderCreateEvent(1L, List.of(new OrderItemEvent(1L, 1)));
    }

    @Test
    public void getOrderById_shouldReturnResponseDto(){
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto result = orderService.getOrderById(1L);

        assertEquals("phone", result.getOrderName());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderMapper, times(1)).toDto(order);
    }

    @Test
    public void getOrderById_shouldThrowExceptionWhenNotFound(){
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResponseStatusException.class, () -> orderService.getOrderById(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void updateOrder_shouldReturnResponseDto(){
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderResponseDto);
        when(orderRepository.save(order)).thenReturn(order);

        OrderResponseDto result = orderService.updateOrder(1L, orderDto);
        assertEquals("phone", result.getOrderName());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void updateOrder_shouldThrowExceptionWhenNotFound(){
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        var ex = assertThrows(ResponseStatusException.class, () -> orderService.updateOrder(1L, orderDto));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(order);
    }

    @Test
    public void createOrder_shouldReturnResponseDto(){
        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderResponseDto);
        OrderResponseDto result = orderService.createOrder(orderDto);
        assertEquals("phone", result.getOrderName());
        verify(messageService, times(1)).sendMessage("order-created", orderCreateEvent);
    }

    @Test
    public void confirmOrder_shouldConfirmOrder(){
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        orderService.confirmOrder(orderCreateEvent);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void confirmOrder_shouldThrowExceptionWhenNotFound(){
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        var ex = assertThrows(ResponseStatusException.class, () -> orderService.confirmOrder(orderCreateEvent));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(order);
    }
}
