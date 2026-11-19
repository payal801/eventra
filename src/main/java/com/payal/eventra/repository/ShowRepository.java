package com.payal.eventra.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByEventId(Long eventId);
    
    boolean existsByEventId(Long eventId);

    boolean existsByVenueId(Long venueId);

    boolean existsByVenueIdAndStartTime(Long venueId, LocalDateTime startTime);

    boolean existsByVenueIdAndStartTimeAndIdNot(Long venueId, LocalDateTime startTime, Long id);
}