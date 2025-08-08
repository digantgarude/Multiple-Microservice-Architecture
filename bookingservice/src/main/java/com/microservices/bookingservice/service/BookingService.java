package com.microservices.bookingservice.service;

import com.microservices.bookingservice.client.InventoryServiceClient;
import com.microservices.bookingservice.entity.Customer;
import com.microservices.bookingservice.event.BookingEvent;
import com.microservices.bookingservice.request.BookingRequest;
import com.microservices.bookingservice.response.BookingResponse;
import com.microservices.bookingservice.response.InventoryResponse;
import com.microservices.bookingservice.respository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class BookingService {
    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    @Autowired
    public BookingService(final CustomerRepository customerRepository,
                          final InventoryServiceClient inventoryServiceClient, KafkaTemplate<String, BookingEvent> kafkaTemplate){
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // Check User exists

        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if(customer == null) {
            throw new RuntimeException("User not found.");
        }

        // Check if there is enough inventory

        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());
        log.info("Inventory Service : "+inventoryResponse);

        // -- get event information to also get Venue information
        if(inventoryResponse.getCapacity() < request.getTicketCount()){
            throw new RuntimeException("Not enough inventory");
        }
        // create booking
        final BookingEvent bookingEvent = createBookingEvent(request, customer, inventoryResponse);


        //Put into kafka Queue

        kafkaTemplate.send("booking", bookingEvent);
        log.info("Booking sent to Kafka {}", bookingEvent);
        // send booking to Order Service on a kafka topic

        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }

    private BookingEvent createBookingEvent(final BookingRequest request,
                                            final Customer customer,
                                            final InventoryResponse inventoryResponse){

        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();
    }
}
