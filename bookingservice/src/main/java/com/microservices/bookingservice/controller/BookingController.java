package com.microservices.bookingservice.controller;

import com.microservices.bookingservice.request.BookingRequest;
import com.microservices.bookingservice.response.BookingResponse;
import com.microservices.bookingservice.service.BookingService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/booking", consumes = "application/json", produces = "application/json")
    @Tool(name = "createBooking", description = "Create a booking for an event when an object containing userId, eventId, ticketCount is passed.")
    public BookingResponse createBooking(@ToolParam(description = "The request json object containing userId, eventId and ticketCount values.") @RequestBody BookingRequest request) {
        System.out.println("Received Booking: " + request.toString());
        return bookingService.createBooking(request);
    }
}
