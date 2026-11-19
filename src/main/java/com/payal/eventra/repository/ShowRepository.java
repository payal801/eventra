package com.payal.eventra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByEventId(Long eventId);
}