package com.tickatch.logservice.log.presentation;

import com.tickatch.logservice.log.application.service.EventLogRegisterService;
import com.tickatch.logservice.log.presentation.dto.EventLogRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event-logs")
@Tag(name = "Event Log API", description = "비즈니스 이벤트 로깅 API")
public class EventLogRegisterApi {

  private final EventLogRegisterService eventLogRegisterService;

  @Operation(summary = "이벤트 로그 등록", description = "비즈니스 이벤트를 EventLog DB에 저장합니다.")
  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void register(@Valid @RequestBody EventLogRegisterRequest request) {
    eventLogRegisterService.register(request.toCommand());
  }
}
