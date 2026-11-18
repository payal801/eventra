package com.payal.eventra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}