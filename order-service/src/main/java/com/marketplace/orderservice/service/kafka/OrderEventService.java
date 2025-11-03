package com.marketplace.orderservice.service.kafka;

import com.marketplace.common.event.InventoryErrorEvent;
import com.marketplace.common.event.OrderCreateEvent;
import org.springframework.stereotype.Component;

public interface OrderEventService {
    void handleSuccess(OrderCreateEvent orderCreateEvent);

    void handleFailure(InventoryErrorEvent inventoryErrorEvent);
}
