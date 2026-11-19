package com.payal.eventra.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ShowResponse {

    private Long id;
    private Long eventId;
    private String eventTitle;
    private Long venueId;
    private String venueName;
    private String city;
    private LocalDateTime startTime;
    private BigDecimal basePrice;
    private Long totalSeats;
    private Long availableSeats;
}
