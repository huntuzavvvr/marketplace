package com.marketplace.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateEvent {
    private Long orderId;
    private List<OrderItemEvent> orderItems;
}
