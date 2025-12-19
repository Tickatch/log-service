package com.tickatch.logservice.ticketlog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_ticket_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketLog {

  @Id
  @Column(name = "id", nullable = false)
  private UUID ticketLogId;

  @Column(name = "ticket_id", nullable = false)
  private UUID ticketId;

  @Column(name = "receive_method")
  private String receiveMethod;

  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType;

  @Column(name = "actor_type", nullable = false, length = 20)
  private String actorType;

  @Column(name = "actor_user_id")
  private UUID actorUserId;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  public static TicketLog create(
      UUID ticketLogId,
      UUID ticketId,
      String receiveMethod,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {

    TicketLog log = new TicketLog();
    log.ticketLogId = ticketLogId;
    log.ticketId = ticketId;
    log.receiveMethod = receiveMethod;
    log.actionType = actionType;
    log.actorType = actorType;
    log.actorUserId = actorUserId;
    log.occurredAt = occurredAt;
    return log;
  }
}
