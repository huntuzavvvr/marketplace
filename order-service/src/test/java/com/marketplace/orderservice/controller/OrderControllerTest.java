package com.marketplace.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.orderservice.dto.OrderDto;
import com.marketplace.orderservice.dto.OrderItemDto;
import com.marketplace.orderservice.dto.OrderResponseDto;
import com.marketplace.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    private OrderResponseDto orderResponseDto;
    private OrderDto orderDto;
    private OrderItemDto orderItemDto;

    @BeforeEach
    public void setup() {
        orderItemDto = new OrderItemDto(2L, 3);
        orderDto = new OrderDto(1L, List.of(orderItemDto));
        orderResponseDto = new OrderResponseDto("new order");
    }

    @Test
    public void getOrderById_shouldReturnResponseDto() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(orderResponseDto);
        mockMvc.perform(get("/api/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderName").value("new order"));
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    public void updateOrder_shouldReturnResponseDto() throws Exception {
        when(orderService.updateOrder(1L, orderDto)).thenReturn(orderResponseDto);
        mockMvc.perform(put("/api/order/1").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderName").value("new order"));
    }

    @Test
    public void createOrder_shouldReturnResponseDto() throws Exception {
        when(orderService.createOrder(orderDto)).thenReturn(orderResponseDto);
        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderName").value("new order"));
        verify(orderService, times(1)).createOrder(orderDto);
    }

    @Test
    public void createOrder_shouldReturnBadRequest() throws Exception {
        OrderDto or = new OrderDto();
        or.setUserId(1L);
        mockMvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(or))).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteOrder_shouldReturnResponseDto() throws Exception {
        mockMvc.perform(delete("/api/order/1")).andExpect(status().isNoContent());
        verify(orderService, times(1)).deleteOrder(1L);
    }


}
