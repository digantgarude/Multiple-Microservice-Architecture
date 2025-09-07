package com.microservices.inventoryservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.microservices.inventoryservice.entity.Event;
import com.microservices.inventoryservice.entity.Venue;
import com.microservices.inventoryservice.repository.EventRepository;
import com.microservices.inventoryservice.repository.VenueRepository;
import com.microservices.inventoryservice.response.VenueInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.inventoryservice.response.EventInventoryResponse;

@Slf4j
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
                .eventId(event.getId())
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build()).collect(Collectors.toList());
    }

    public EventInventoryResponse getEventInventory(final Long eventId){
        final Event event = eventRepository.findById(eventId).orElse(null);

        return EventInventoryResponse.builder()
                .eventId(event.getId())
                .event(event.getName())
                .capacity(event.getLeftCapacity())
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

    public void updateEventCapacity(final Long eventId, final Long ticketsBooked){
        final Event event = eventRepository.findById(eventId).orElse(null);
        log.info("Found event : id:  {} ticketsBooked : {}", eventId, ticketsBooked);

        event.setLeftCapacity(event.getLeftCapacity() - ticketsBooked);
        eventRepository.saveAndFlush(event);
        log.info("Updated event capacity for event id:  {} with tickets booked : {}", eventId, ticketsBooked);
    }

}
