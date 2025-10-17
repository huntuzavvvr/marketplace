package com.marketplace.orderservice.service.kafka;

import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<Void> sendMessage(String message);
}
