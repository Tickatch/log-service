package com.tickatch.logservice.arthalllog.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ArtHallLogEvent(
    UUID eventId,
    String domainType,
    Long domainId,
    String actionType,
    String actorType,
    UUID actorUserId,
    LocalDateTime occurredAt) {}
