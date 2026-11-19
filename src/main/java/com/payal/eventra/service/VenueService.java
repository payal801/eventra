package com.payal.eventra.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payal.eventra.dto.VenueRequest;
import com.payal.eventra.dto.VenueResponse;
import com.payal.eventra.entity.Venue;
import com.payal.eventra.exception.ConflictException;
import com.payal.eventra.exception.ResourceNotFoundException;
import com.payal.eventra.repository.ShowRepository;
import com.payal.eventra.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final ShowRepository showRepository;

    public VenueResponse create(VenueRequest request) {
        Venue venue = Venue.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .totalRows(request.getTotalRows())
                .seatsPerRow(request.getSeatsPerRow())
                .build();

        return toResponse(venueRepository.save(venue));
    }

    public List<VenueResponse> getAll() {
        return venueRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public VenueResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public VenueResponse update(Long id, VenueRequest request) {
        Venue venue = findOrThrow(id);

        venue.setName(request.getName());
        venue.setCity(request.getCity());
        venue.setAddress(request.getAddress());
        venue.setTotalRows(request.getTotalRows());
        venue.setSeatsPerRow(request.getSeatsPerRow());

        return toResponse(venue);
    }

    public void delete(Long id) {
        Venue venue = findOrThrow(id);

        if (showRepository.existsByVenueId(id)) {
            throw new ConflictException("Cannot delete a venue that has shows. Delete its shows first.");
        }

        venueRepository.delete(venue);
    }

    private Venue findOrThrow(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
    }

    private VenueResponse toResponse(Venue venue) {
        return VenueResponse.builder()
                .id(venue.getId())
                .name(venue.getName())
                .city(venue.getCity())
                .address(venue.getAddress())
                .totalRows(venue.getTotalRows())
                .seatsPerRow(venue.getSeatsPerRow())
                .totalSeats(venue.getTotalRows() * venue.getSeatsPerRow())
                .build();
    }
}