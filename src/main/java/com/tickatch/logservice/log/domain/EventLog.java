package com.tickatch.logservice.log.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Entity
@Table(name = "p_event_log")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class EventLog {

  @Id
  @Column(name = "id", columnDefinition = "UUID")
  private UUID logId;

  @Column(name = "event_category", length = 50, nullable = false)
  private String eventCategory;

  @Column(name = "event_type", length = 100, nullable = false)
  private String eventType;

  @Column(name = "action_type", length = 30, nullable = false)
  private String actionType;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "event_detail", columnDefinition = "jsonb")
  private String eventDetail;

  @Column(name = "user_id", columnDefinition = "UUID")
  private UUID userId;

  @Column(name = "resource_id", length = 100)
  private String resourceId;

  @Column(name = "ip_address", length = 50)
  private String ipAddress;

  @Column(name = "trace_id", length = 64)
  private String traceId;

  @Column(name = "service_name", length = 100, nullable = false)
  private String serviceName;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  private EventLog(
      String eventCategory,
      String eventType,
      String actionType,
      String eventDetail,
      UUID userId,
      String resourceId,
      String ipAddress,
      String traceId,
      String serviceName) {
    this.logId = UUID.randomUUID();
    this.eventCategory = eventCategory;
    this.eventType = eventType;
    this.actionType = actionType;
    this.eventDetail = eventDetail;
    this.userId = userId;
    this.resourceId = resourceId;
    this.ipAddress = ipAddress;
    this.traceId = traceId;
    this.serviceName = serviceName;
    this.createdAt = LocalDateTime.now();
  }

  public static EventLog create(
      String eventCategory,
      String eventType,
      String actionType,
      String eventDetail,
      UUID userId,
      String resourceId,
      String ipAddress,
      String traceId,
      String serviceName) {
    return new EventLog(
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
