package com.tickatch.logservice.log.application.dto;

import java.util.UUID;

public record EventLogRegisterCommand(
    String eventCategory,
    String eventType,
    String actionType,
    String eventDetail,
    String deviceInfo,
    UUID userId,
    String resourceId,
    String ipAddress,
    String traceId,
    String serviceName) {}
