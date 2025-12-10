package com.tickatch.logservice.log.application.service;

import com.tickatch.logservice.log.application.dto.EventLogResult;
import com.tickatch.logservice.log.domain.EventLog;
import com.tickatch.logservice.log.domain.exception.LogErrorCode;
import com.tickatch.logservice.log.domain.repository.EventLogQueryRepository;
import com.tickatch.logservice.log.domain.search.EventLogSearchCondition;
import io.github.tickatch.common.error.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventLogQueryService {

  private final EventLogQueryRepository eventLogQueryRepository;

  public EventLogResult getOne(UUID logId) {

    EventLog log = eventLogQueryRepository.findOneByLogId(logId);

    if (log == null) {
      throw new BusinessException(LogErrorCode.LOG_NOT_FOUND);
    }

    return EventLogResult.from(log);
  }

  public Page<EventLogResult> getList(EventLogSearchCondition condition, Pageable pageable) {

    Page<EventLog> logs = eventLogQueryRepository.findList(condition, pageable);

    return logs.map(EventLogResult::from);
  }
}
