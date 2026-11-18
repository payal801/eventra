package com.payal.eventra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
