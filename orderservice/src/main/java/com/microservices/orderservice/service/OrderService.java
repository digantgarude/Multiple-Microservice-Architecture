package com.microservices.orderservice.service;

import com.microservices.bookingservice.event.BookingEvent;
import com.microservices.orderservice.client.InventoryServiceClient;
import com.microservices.orderservice.entity.Order;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    private OrderRepository orderRepository;
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryServiceClient inventoryServiceClient){
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }


    @KafkaListener(topics = "booking", groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent) {
        log.info("Received order booking event : {}",bookingEvent);
        // Create Order obj to DB
        Order order = createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);

        // Update inventory
        inventoryServiceClient.updateInventory(order.getEventId(), order.getTicketCount());

        log.info("Inventory updated for event :{}, less tickets: {}", order.getEventId(), order.getTicketCount());
    }

    private Order createOrder(BookingEvent bookingEvent) {
        return Order.builder()
                .customerId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }
}
