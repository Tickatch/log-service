package com.tickatch.logservice.authlog.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuthEvent(
    UUID eventId, // 발행 서비스에서 생성
    String actionType, // LOGIN_SUCCESS / LOGIN_FAILED / ACCOUNT_LOCKED ...
    String actorType, // USER / SYSTEM
    UUID actorUserId, // auth_id
    LocalDateTime occurredAt) {}
