package com.tickatch.logservice.reservationlog.domain.repository;

import com.tickatch.logservice.reservationlog.domain.ReservationLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationLogRepository extends JpaRepository<ReservationLog, UUID> {}
