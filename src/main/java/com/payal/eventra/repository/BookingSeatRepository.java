package com.payal.eventra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
}