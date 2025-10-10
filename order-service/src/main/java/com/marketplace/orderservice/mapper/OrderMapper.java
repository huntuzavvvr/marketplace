package com.marketplace.orderservice.mapper;

import com.marketplace.orderservice.dto.OrderResponseDto;
import com.marketplace.orderservice.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDto toDto(Order order);
}
