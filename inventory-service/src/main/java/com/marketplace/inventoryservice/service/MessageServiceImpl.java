package com.marketplace.inventoryservice.service;

import com.marketplace.common.event.OrderCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl {
    private final InventoryServiceImpl inventoryService;

    private final KafkaTemplate<String, OrderCreateEvent> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "inventory-service-group")
    public void handleEvent(OrderCreateEvent event) {
        System.out.println("Received OrderCreateEvent : " + event);
        try {
            inventoryService.createOrder(event);
            sendMessage("inventory-reserved", event);
        }
        catch (Exception e) {
            sendMessage("inventory-error", event);
        }
    }

    public void sendMessage(String topic, OrderCreateEvent orderCreateEvent) {
        kafkaTemplate.send(topic, orderCreateEvent);
    }

}
