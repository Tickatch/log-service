package com.tickatch.logservice.productlog.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductEvent(
    UUID eventId, // 발행 서비스에서 생성하는 이벤트 ID
    Long productId,
    String actionType,
    // CREATED, UPDATED, SUBMITTED_FOR_APPROVAL, APPROVED, REJECTED, RESUBMITTED, SALE_SCHEDULED,
    // SALE_STARTED, SALE_CLOSED, COMPLETED, CANCELLED ...
    String actorType,
    UUID actorUserId,
    LocalDateTime occurredAt) {}
