package com.payal.eventra.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowRequest {

    @NotNull(message = "Event id is required")
    private Long eventId;

    @NotNull(message = "Venue id is required")
    private Long venueId;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", message = "Base price cannot be negative")
    @Digits(integer = 8, fraction = 2, message = "Base price must have at most 2 decimal places")
    private BigDecimal basePrice;
}
