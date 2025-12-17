package com.tickatch.logservice.arthalllog.domain.repository;

import com.tickatch.logservice.arthalllog.domain.ArtHallLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtHallLogRepository extends JpaRepository<ArtHallLog, UUID> {}
