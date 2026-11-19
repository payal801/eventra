package com.payal.eventra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payal.eventra.entity.ShowSeat;
import com.payal.eventra.enums.SeatStatus;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
	 List<ShowSeat> findByShowId(Long showId);

	    long countByShowId(Long showId);

	    long countByShowIdAndStatus(Long showId, SeatStatus status);

	    void deleteByShowId(Long showId);
}
