package com.tickatch.logservice.log.domain.search;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventLogSearchCondition(
    String eventCategory,
    String eventType,
    String actionType,
    UUID userId,
    String serviceName,
    String keyword,
    LocalDateTime from,
    LocalDateTime to) {}
