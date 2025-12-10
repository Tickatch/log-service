package com.tickatch.logservice.log.presentation;

import com.tickatch.logservice.log.application.dto.EventLogResult;
import com.tickatch.logservice.log.application.service.EventLogQueryService;
import com.tickatch.logservice.log.domain.search.EventLogSearchCondition;
import com.tickatch.logservice.log.presentation.dto.EventLogListResponse;
import com.tickatch.logservice.log.presentation.dto.EventLogResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event-logs")
@Tag(name = "Event Log 조회 API", description = "EventLog 조회 기능 제공")
public class EventLogQueryApi {

  private final EventLogQueryService eventLogQueryService;

  @Operation(summary = "이벤트 로그 단건 조회")
  @GetMapping("/{logId}")
  public ApiResponse<EventLogResponse> getOne(@PathVariable UUID logId) {
    EventLogResult result = eventLogQueryService.getOne(logId);
    return ApiResponse.success(EventLogResponse.from(result));
  }

  @Operation(summary = "이벤트 로그 조회", description = "필터와 키워드를 이용하여 로그를 조회합니다.")
  @GetMapping
  public ApiResponse<Page<EventLogListResponse>> getLogs(
      EventLogSearchCondition condition, Pageable pageable) {
    Page<EventLogResult> result = eventLogQueryService.getList(condition, pageable);
    return ApiResponse.success(result.map(EventLogListResponse::from));
  }
}
