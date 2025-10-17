package com.marketplace.orderservice.service.kafka;

import com.marketplace.common.event.OrderCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate<String, OrderCreateEvent> kafkaTemplate;

    @Override
    public Mono<Void> sendMessage(String message) {
        return null;
    }

    public void sendMessage(String topic, OrderCreateEvent orderCreateEvent) {
        kafkaTemplate.send(topic, orderCreateEvent);
    }


}

