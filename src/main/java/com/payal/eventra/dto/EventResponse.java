package com.payal.eventra.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String language;
    private Integer durationMinutes;
    private String posterUrl;
    private Long organizerId;
    private String organizerName;
    private LocalDateTime createdAt;
}