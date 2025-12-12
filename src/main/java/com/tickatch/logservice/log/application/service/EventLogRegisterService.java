package com.tickatch.logservice.log.application.service;

import com.tickatch.logservice.log.application.dto.EventLogRegisterCommand;
import com.tickatch.logservice.log.domain.EventLog;
import com.tickatch.logservice.log.domain.repository.EventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventLogRegisterService {

  private final EventLogRepository eventLogRepository;

  @Transactional
  public void register(EventLogRegisterCommand command) {
    EventLog eventLog =
        EventLog.create(
            command.eventCategory(),
            command.eventType(),
            command.actionType(),
            command.eventDetail(),
            command.deviceInfo(),
            command.userId(),
            command.resourceId(),
            command.ipAddress(),
            command.traceId(),
            command.serviceName());

    eventLogRepository.save(eventLog);
  }
}
