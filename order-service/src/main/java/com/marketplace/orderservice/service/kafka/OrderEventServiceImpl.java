package com.marketplace.orderservice.service.kafka;

import com.marketplace.common.event.InventoryErrorEvent;
import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.orderservice.service.OrderService;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

    private final OrderService orderService;
    private final Tracer tracer;

    @KafkaListener(topics = "inventory-reserved", groupId = "order-service-group")
    public void handleSuccess(OrderCreateEvent orderCreateEvent) {
        orderService.confirmOrder(orderCreateEvent);
    }

    @KafkaListener(topics = "inventory-error", groupId = "order-service-group")
    public void handleFailure(InventoryErrorEvent inventoryErrorEvent) {
        System.out.println(inventoryErrorEvent.getMessage());
    }
}
