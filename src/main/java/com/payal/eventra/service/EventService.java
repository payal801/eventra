package com.payal.eventra.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payal.eventra.dto.EventRequest;
import com.payal.eventra.dto.EventResponse;
import com.payal.eventra.entity.Event;
import com.payal.eventra.entity.User;
import com.payal.eventra.exception.ResourceNotFoundException;
import com.payal.eventra.repository.EventRepository;
import com.payal.eventra.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public EventResponse create(EventRequest request) {
        User organizer = userRepository.findById(request.getOrganizerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id " + request.getOrganizerId()));

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .language(request.getLanguage())
                .durationMinutes(request.getDurationMinutes())
                .posterUrl(request.getPosterUrl())
                .organizer(organizer)
                .build();

        return toResponse(eventRepository.save(event));
    }

    public List<EventResponse> getAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EventResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public EventResponse update(Long id, EventRequest request) {
        Event event = findOrThrow(id);

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setLanguage(request.getLanguage());
        event.setDurationMinutes(request.getDurationMinutes());
        event.setPosterUrl(request.getPosterUrl());

        return toResponse(event);
    }

    @Transactional
    public void delete(Long id) {
        Event event = findOrThrow(id);
        eventRepository.delete(event);
    }

    private Event findOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    private EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .category(event.getCategory())
                .language(event.getLanguage())
                .durationMinutes(event.getDurationMinutes())
                .posterUrl(event.getPosterUrl())
                .organizerId(event.getOrganizer().getId())
                .organizerName(event.getOrganizer().getName())
                .createdAt(event.getCreatedAt())
                .build();
    }
}