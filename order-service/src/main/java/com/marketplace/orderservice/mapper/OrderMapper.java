package com.marketplace.orderservice.mapper;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.orderservice.dto.OrderDto;
import com.marketplace.orderservice.dto.OrderResponseDto;
import com.marketplace.orderservice.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDto toDto(Order order);
    Order toEntity(OrderDto orderDto);

}
