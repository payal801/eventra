package com.payal.eventra.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueRequest {

    @NotBlank(message = "Venue name is required")
    @Size(max = 150, message = "Name must be at most 150 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must be at most 100 characters")
    private String city;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be at most 255 characters")
    private String address;

    @NotNull(message = "Total rows is required")
    @Min(value = 1, message = "Total rows must be at least 1")
    @Max(value = 100, message = "Total rows must be at most 100")
    private Integer totalRows;

    @NotNull(message = "Seats per row is required")
    @Min(value = 1, message = "Seats per row must be at least 1")
    @Max(value = 100, message = "Seats per row must be at most 100")
    private Integer seatsPerRow;
}
