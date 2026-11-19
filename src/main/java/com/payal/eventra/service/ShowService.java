package com.payal.eventra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payal.eventra.dto.ShowRequest;
import com.payal.eventra.dto.ShowResponse;
import com.payal.eventra.dto.ShowUpdateRequest;
import com.payal.eventra.entity.Event;
import com.payal.eventra.entity.Show;
import com.payal.eventra.entity.ShowSeat;
import com.payal.eventra.entity.Venue;
import com.payal.eventra.enums.SeatStatus;
import com.payal.eventra.exception.ConflictException;
import com.payal.eventra.exception.ResourceNotFoundException;
import com.payal.eventra.repository.BookingRepository;
import com.payal.eventra.repository.EventRepository;
import com.payal.eventra.repository.ShowRepository;
import com.payal.eventra.repository.ShowSeatRepository;
import com.payal.eventra.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public ShowResponse create(ShowRequest request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event not found with id " + request.getEventId()));

        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Venue not found with id " + request.getVenueId()));

        Show show = Show.builder()
                .event(event)
                .venue(venue)
                .startTime(request.getStartTime())
                .basePrice(request.getBasePrice())
                .build();

        show = showRepository.save(show);
        generateSeats(show, venue);

        return toResponse(show);
    }

    public List<ShowResponse> getAll(Long eventId) {
        List<Show> shows = (eventId == null)
                ? showRepository.findAll()
                : showRepository.findByEventId(eventId);

        return shows.stream()
                .map(this::toResponse)
                .toList();
    }

    public ShowResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public ShowResponse update(Long id, ShowUpdateRequest request) {
        Show show = findOrThrow(id);

        show.setStartTime(request.getStartTime());
        show.setBasePrice(request.getBasePrice());

        showSeatRepository.findByShowId(id).stream()
                .filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE)
                .forEach(seat -> seat.setPrice(request.getBasePrice()));

        return toResponse(show);
    }

    @Transactional
    public void delete(Long id) {
        Show show = findOrThrow(id);

        if (bookingRepository.existsByShowId(id)) {
            throw new ConflictException("Cannot delete a show that already has bookings");
        }

        showSeatRepository.deleteByShowId(id);
        showRepository.delete(show);
    }

    private void generateSeats(Show show, Venue venue) {
        List<ShowSeat> seats = new ArrayList<>();

        for (int row = 0; row < venue.getTotalRows(); row++) {
            String rowLabel = rowLabel(row);

            for (int number = 1; number <= venue.getSeatsPerRow(); number++) {
                seats.add(ShowSeat.builder()
                        .show(show)
                        .seatLabel(rowLabel + number)
                        .price(show.getBasePrice())
                        .status(SeatStatus.AVAILABLE)
                        .build());
            }
        }

        showSeatRepository.saveAll(seats);
    }

    // 0 -> A, 1 -> B, ... 25 -> Z, 26 -> AA, 27 -> AB ...
    private String rowLabel(int index) {
        StringBuilder label = new StringBuilder();
        int n = index;
        do {
            label.insert(0, (char) ('A' + n % 26));
            n = n / 26 - 1;
        } while (n >= 0);
        return label.toString();
    }

    private Show findOrThrow(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id " + id));
    }

    private ShowResponse toResponse(Show show) {
        long total = showSeatRepository.countByShowId(show.getId());
        long available = showSeatRepository.countByShowIdAndStatus(show.getId(), SeatStatus.AVAILABLE);

        return ShowResponse.builder()
                .id(show.getId())
                .eventId(show.getEvent().getId())
                .eventTitle(show.getEvent().getTitle())
                .venueId(show.getVenue().getId())
                .venueName(show.getVenue().getName())
                .city(show.getVenue().getCity())
                .startTime(show.getStartTime())
                .basePrice(show.getBasePrice())
                .totalSeats(total)
                .availableSeats(available)
                .build();
    }
}