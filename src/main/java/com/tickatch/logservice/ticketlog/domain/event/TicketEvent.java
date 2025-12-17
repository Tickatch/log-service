package com.tickatch.logservice.ticketlog.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketEvent(
    UUID eventId, // 발행 서비스에서 생성하는 이벤트 ID
    UUID ticketId,
    String receiveMethod, // 수령 이벤트일 때만 값 존재 (ON_SITE 등)
    String actionType, // ISSUED / USED / CANCELED / EXPIRED
    String actorType,
    UUID actorUserId,
    LocalDateTime occurredAt) {}
