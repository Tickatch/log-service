package com.tickatch.logservice.userlog.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserEvent(
    UUID eventId, // 발행 서비스에서 생성한 이벤트 ID
    UUID userId,
    String actionType, // CREATED / UPDATED / DEACTIVATED / DELETED ...
    String actorType,
    UUID actorUserId,
    LocalDateTime occurredAt) {}
