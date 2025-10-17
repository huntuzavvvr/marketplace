package com.marketplace.orderservice.service.kafka;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

    private final OrderService orderService;


    @KafkaListener(topics = "inventory-reserved", groupId = "order-service-group")
    public void handleSuccess(OrderCreateEvent orderCreateEvent) {
        orderService.confirmOrder(orderCreateEvent);
    }

    @KafkaListener(topics = "inventory-error", groupId = "order-service-group")
    public void handleFailure(String message) {
    }
}
