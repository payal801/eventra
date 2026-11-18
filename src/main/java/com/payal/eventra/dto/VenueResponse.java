package com.payal.eventra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VenueResponse {

    private Long id;
    private String name;
    private String city;
    private String address;
    private Integer totalRows;
    private Integer seatsPerRow;
    private Integer totalSeats;
}
