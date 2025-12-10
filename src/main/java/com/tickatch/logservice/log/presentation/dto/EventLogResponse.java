package com.tickatch.logservice.log.presentation.dto;

import com.tickatch.logservice.log.application.dto.EventLogResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventLogResponse(
    UUID logId,
    String eventCategory,
    String eventType,
    String actionType,
    String eventDetail,
    String deviceInfo,
    UUID userId,
    String resourceId,
    String ipAddress,
    String traceId,
    String serviceName,
    LocalDateTime createdAt) {

  public static EventLogResponse from(EventLogResult result) {
    return new EventLogResponse(
        result.logId(),
        result.eventCategory(),
        result.eventType(),
        result.actionType(),
        result.eventDetail(),
        result.deviceInfo(),
        result.userId(),
        result.resourceId(),
        result.ipAddress(),
        result.traceId(),
        result.serviceName(),
        result.createdAt());
  }
}
