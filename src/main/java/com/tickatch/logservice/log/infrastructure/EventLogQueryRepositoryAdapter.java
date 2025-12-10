package com.tickatch.logservice.log.infrastructure;

import static com.tickatch.logservice.log.domain.QEventLog.eventLog;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickatch.logservice.log.domain.EventLog;
import com.tickatch.logservice.log.domain.repository.EventLogQueryRepository;
import com.tickatch.logservice.log.domain.search.EventLogSearchCondition;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventLogQueryRepositoryAdapter implements EventLogQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public EventLog findOneByLogId(UUID id) {

    return queryFactory.selectFrom(eventLog).where(eventLog.logId.eq(id)).fetchOne();
  }

  @Override
  public Page<EventLog> findList(EventLogSearchCondition condition, Pageable pageable) {

    List<EventLog> logs =
        queryFactory
            .selectFrom(eventLog)
            .where(
                eventCategoryEq(condition.eventCategory()),
                eventTypeEq(condition.eventType()),
                actionTypeEq(condition.actionType()),
                userIdEq(condition.userId()),
                serviceNameEq(condition.serviceName()),
                createdAtBetween(condition.from(), condition.to()),
                keywordContains(condition.keyword()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    Long total =
        queryFactory
            .select(eventLog.count())
            .from(eventLog)
            .where(
                eventCategoryEq(condition.eventCategory()),
                eventTypeEq(condition.eventType()),
                actionTypeEq(condition.actionType()),
                userIdEq(condition.userId()),
                serviceNameEq(condition.serviceName()),
                createdAtBetween(condition.from(), condition.to()),
                keywordContains(condition.keyword()))
            .fetchOne();

    if (total == null) {
      total = 0L;
    }

    return new PageImpl<>(logs, pageable, total);
  }

  private BooleanExpression eventCategoryEq(String category) {
    return category == null ? null : eventLog.eventCategory.eq(category);
  }

  private BooleanExpression eventTypeEq(String type) {
    return type == null ? null : eventLog.eventType.eq(type);
  }

  private BooleanExpression actionTypeEq(String type) {
    return type == null ? null : eventLog.actionType.eq(type);
  }

  private BooleanExpression userIdEq(UUID userId) {
    return userId == null ? null : eventLog.userId.eq(userId);
  }

  private BooleanExpression serviceNameEq(String serviceName) {
    return serviceName == null ? null : eventLog.serviceName.eq(serviceName);
  }

  private BooleanExpression createdAtBetween(LocalDateTime from, LocalDateTime to) {
    if (from == null || to == null) {
      return null;
    }
    return eventLog.createdAt.between(from, to);
  }

  private BooleanExpression keywordContains(String keyword) {
    if (keyword == null || keyword.isBlank()) {
      return null;
    }

    return eventLog
        .eventCategory
        .containsIgnoreCase(keyword)
        .or(eventLog.eventType.containsIgnoreCase(keyword))
        .or(eventLog.actionType.containsIgnoreCase(keyword))
        .or(eventLog.resourceId.containsIgnoreCase(keyword))
        .or(eventLog.serviceName.containsIgnoreCase(keyword));
  }
}
