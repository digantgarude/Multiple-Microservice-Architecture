package com.microservices.bookingservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    @ToolParam(description = "The unique identifier of the user requesting tickets ")
    private long userId;

    @ToolParam(description = "The unique identifier of the event")
    private long eventId;

    @ToolParam(description = "The number of tickets to book (must be positive)")
    private long ticketCount;
}
