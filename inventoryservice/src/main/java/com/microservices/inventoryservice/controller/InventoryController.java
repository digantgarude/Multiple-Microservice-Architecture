package com.microservices.inventoryservice.controller;

import com.microservices.inventoryservice.response.VenueInventoryResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.microservices.inventoryservice.response.EventInventoryResponse;
import com.microservices.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {
    private InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events")
    @Tool(name = "inventoryGetAllEvents", description = "Get a list of all the events.")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllEvents() {
        return inventoryService.getAllEvents();
    }

    @GetMapping("/inventory/event/{eventId}")
    @Tool(name = "inventoryForEvent", description = "Get the inventory details when the id of an event is passed.")
    public @ResponseBody EventInventoryResponse inventoryForEvent(@ToolParam(description = "The event identifier") @PathVariable("eventId") Long eventId) {
        return inventoryService.getEventInventory(eventId);
    }

    @GetMapping("/inventory/venue/{venueId}")
    @Tool(name = "inventoryByVenueId", description = "Get the inventory details when the id of a venue is passed.")
    public @ResponseBody VenueInventoryResponse inventoryByVenueId(@ToolParam(description = "The venue identifier") @PathVariable("venueId") Long venueId) {
        return  inventoryService.getVenueInformation(venueId);
    }

    /*
    @PutMapping("/inventory/event/{eventId}/tickets-booked/{ticketsBooked}")
    @Tool(name = "updateEventCapacity", description = "Update the event capacity for an event when the id of a event is passed and the number of tickets booked is passed.")
    public ResponseEntity<Void> updateEventCapacity(@ToolParam(description = "The event identifier")  @PathVariable("eventId") Long eventId,
                                                    @ToolParam(description = "The number of tickets booked")  @PathVariable("ticketsBooked") Long ticketsBooked){
        inventoryService.updateEventCapacity(eventId, ticketsBooked);
        return ResponseEntity.ok().build();

    }
     */

    @PutMapping("/inventory/event/{eventId}/tickets-booked/{ticketsBooked}")
    public ResponseEntity<Void> updateEventCapacity(@PathVariable("eventId") Long eventId,
                                                    @PathVariable("ticketsBooked") Long ticketsBooked){
        inventoryService.updateEventCapacity(eventId, ticketsBooked);
        return ResponseEntity.ok().build();

    }

}
