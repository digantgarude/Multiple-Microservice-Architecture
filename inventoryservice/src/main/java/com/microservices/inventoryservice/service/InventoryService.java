package com.microservices.inventoryservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.microservices.inventoryservice.entity.Event;
import com.microservices.inventoryservice.entity.Venue;
import com.microservices.inventoryservice.repository.EventRepository;
import com.microservices.inventoryservice.repository.VenueRepository;
import com.microservices.inventoryservice.response.VenueInventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.inventoryservice.response.EventInventoryResponse;

@Service
public class InventoryService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public InventoryService(final EventRepository eventRepository, final VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<EventInventoryResponse> getAllEvents() {
        final List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeft_capacity())
                .venue(event.getVenue())
                .build()).collect(Collectors.toList());
    }
    public EventInventoryResponse getEventInventory(final Long eventId){
        final Event event = eventRepository.findById(eventId).orElse(null);

        return EventInventoryResponse.builder()
                .eventId(event.getId())
                .event(event.getName())
                .capacity(event.getLeft_capacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .build();
    }

    public VenueInventoryResponse getVenueInformation(final Long venueId){
        final Venue venue = venueRepository.findById(venueId).orElse(null);

        assert venue != null;
        return VenueInventoryResponse.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotal_capacity())
                .build();
    }

}
