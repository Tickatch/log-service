package com.tickatch.logservice.reservationlog.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationEvent(
    UUID eventId, // 발행 서비스에서 생성하는 이벤트 ID
    UUID reservationId,
    String actionType, // CREATED / CONFIRMED / CANCELLED ...
    String actorType,
    UUID actorUserId,
    LocalDateTime occurredAt) {}
