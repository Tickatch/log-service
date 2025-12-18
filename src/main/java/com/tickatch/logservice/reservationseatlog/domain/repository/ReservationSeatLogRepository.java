package com.tickatch.logservice.reservationseatlog.domain.repository;

import com.tickatch.logservice.reservationseatlog.domain.ReservationSeatLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatLogRepository extends JpaRepository<ReservationSeatLog, UUID> {}
