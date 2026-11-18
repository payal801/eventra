package com.payal.eventra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
