package com.marketplace.notificationservice.service.kafka;

import com.marketplace.common.event.OrderCreateEvent;
import com.marketplace.notificationservice.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final KafkaTemplate<String, OrderCreateEvent> kafkaTemplate;
    private final EmailServiceImpl  emailService;

    @KafkaListener(topics = "order-created", groupId = "notification-service")
    public void handleSuccess(OrderCreateEvent orderCreateEvent) {
        System.out.println("Received order created: " + orderCreateEvent);
        emailService.sendEmail("poplarka1@yandex.ru", "order", "Ваш заказ создан!");
    }
}
