package com.payal.eventra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
