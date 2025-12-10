package com.tickatch.logservice.log.domain.repository;

import com.tickatch.logservice.log.domain.EventLog;
import com.tickatch.logservice.log.domain.search.EventLogSearchCondition;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventLogQueryRepository {

  EventLog findOneByLogId(UUID logId);

  Page<EventLog> findList(EventLogSearchCondition condition, Pageable pageable);
}
