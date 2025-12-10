package com.tickatch.logservice.log.presentation.dto;

import com.tickatch.logservice.log.application.dto.EventLogRegisterCommand;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record EventLogRegisterRequest(
    @NotBlank(message = "eventCategory는 필수 값입니다.") String eventCategory,
    @NotBlank(message = "eventType은 필수 값입니다.") String eventType,
    @NotBlank(message = "actionType은 필수 값입니다.") String actionType,
    String eventDetail,
    UUID userId,
    String resourceId,
    String ipAddress,
    String traceId,
    @NotBlank(message = "serviceName은 필수 값입니다.") String serviceName) {

  public EventLogRegisterCommand toCommand() {
    return new EventLogRegisterCommand(
        eventCategory,
        eventType,
        actionType,
        eventDetail,
        userId,
        resourceId,
        ipAddress,
        traceId,
        serviceName);
  }
}
