package com.tickatch.logservice.log.domain.repository;

import com.tickatch.logservice.log.domain.EventLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, UUID> {}
