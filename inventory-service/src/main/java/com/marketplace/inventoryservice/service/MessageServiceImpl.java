package com.marketplace.inventoryservice.service;

import com.marketplace.common.event.InventoryErrorEvent;
import com.marketplace.common.event.OrderCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl {
    private final InventoryServiceImpl inventoryService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "inventory-service-group")
    public void handleEvent(OrderCreateEvent event) {
        System.out.println("Received OrderCreateEvent : " + event);
        try {
            inventoryService.createOrder(event);
            sendMessage("inventory-reserved", event);
        }
        catch (NullPointerException e) {
            sendMessage("inventory-error", new InventoryErrorEvent(event.getOrderId(), "product not found in inventory"));
        }
        catch (RuntimeException e) {
            sendMessage("inventory-error", new InventoryErrorEvent(event.getOrderId(), e.getMessage()));
        }
    }

    public void sendMessage(String topic, Object event) {
        kafkaTemplate.send(topic, event);
    }

}
