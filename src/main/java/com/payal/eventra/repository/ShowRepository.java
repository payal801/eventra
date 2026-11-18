package com.payal.eventra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
