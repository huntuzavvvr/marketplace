package com.marketplace.orderservice.mapper;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.orderservice.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEventMapper {
    OrderCreateEvent toDto(Order order);
}
