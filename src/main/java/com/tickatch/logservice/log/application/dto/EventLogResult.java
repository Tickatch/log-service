package com.tickatch.logservice.log.application.dto;

import com.tickatch.logservice.log.domain.EventLog;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventLogResult(
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

  public static EventLogResult from(EventLog log) {
    return new EventLogResult(
        log.getLogId(),
        log.getEventCategory(),
        log.getEventType(),
        log.getActionType(),
        log.getEventDetail(),
        log.getDeviceInfo(),
        log.getUserId(),
        log.getResourceId(),
        log.getIpAddress(),
        log.getTraceId(),
        log.getServiceName(),
        log.getCreatedAt());
  }
}
