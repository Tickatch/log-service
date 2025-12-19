package com.tickatch.logservice.ticketlog.domain.repository;

import com.tickatch.logservice.ticketlog.domain.TicketLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketLogRepository extends JpaRepository<TicketLog, UUID> {}
