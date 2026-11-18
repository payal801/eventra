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
public class EventRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    private String title;

    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String description;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be at most 50 characters")
    private String category;

    @Size(max = 50, message = "Language must be at most 50 characters")
    private String language;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1000, message = "Duration must be at most 1000 minutes")
    private Integer durationMinutes;

    @Size(max = 255, message = "Poster URL must be at most 255 characters")
    private String posterUrl;

    @NotNull(message = "Organizer id is required")
    private Long organizerId;
}