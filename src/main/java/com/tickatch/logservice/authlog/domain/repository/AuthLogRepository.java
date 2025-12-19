package com.tickatch.logservice.authlog.domain.repository;

import com.tickatch.logservice.authlog.domain.AuthLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthLogRepository extends JpaRepository<AuthLog, UUID> {}
