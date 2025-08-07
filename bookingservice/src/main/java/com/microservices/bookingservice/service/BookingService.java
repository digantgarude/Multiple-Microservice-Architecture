package com.microservices.bookingservice.service;

import com.microservices.bookingservice.client.InventoryServiceClient;
import com.microservices.bookingservice.entity.Customer;
import com.microservices.bookingservice.request.BookingRequest;
import com.microservices.bookingservice.response.BookingResponse;
import com.microservices.bookingservice.response.InventoryResponse;
import com.microservices.bookingservice.respository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;

    @Autowired
    public BookingService(final CustomerRepository customerRepository, InventoryServiceClient inventoryServiceClient){
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public BookingResponse createBooking(final BookingRequest request) {
        // Check User exists

        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if(customer == null) {
            throw new RuntimeException("User not found.");
        }

        // Check if there is enough inventory

        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());
        System.out.println("Inventory Service : "+inventoryResponse);

        // -- get event information to also get Venue information
        // create booking
        // send booking to Order Service on a kafka topic
        return BookingResponse.builder().build();
    }
}
