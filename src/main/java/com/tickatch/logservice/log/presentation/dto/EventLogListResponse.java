package com.tickatch.logservice.log.presentation.dto;

import com.tickatch.logservice.log.application.dto.EventLogResult;

public record EventLogListResponse(
    String eventCategory,
    String eventType,
    String actionType,
    String userId,
    String serviceName,
    String createdAt) {

  public static EventLogListResponse from(EventLogResult result) {
    return new EventLogListResponse(
        result.eventCategory(),
        result.eventType(),
        result.actionType(),
        result.userId() == null ? null : result.userId().toString(),
        result.serviceName(),
        result.createdAt().toString());
  }
}
