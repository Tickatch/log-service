package com.tickatch.logservice.reservationlog.domain;

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
@Table(name = "p_reservation_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationLog {

  @Id
  @Column(name = "id", nullable = false)
  private UUID reservationLogId;

  @Column(name = "reservation_id", nullable = false)
  private UUID reservationId;

  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType;

  @Column(name = "actor_type", nullable = false, length = 20)
  private String actorType;

  @Column(name = "actor_user_id")
  private UUID actorUserId;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  public static ReservationLog create(
      UUID reservationLogId,
      UUID reservationId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {

    ReservationLog log = new ReservationLog();
    log.reservationLogId = reservationLogId;
    log.reservationId = reservationId;
    log.actionType = actionType;
    log.actorType = actorType;
    log.actorUserId = actorUserId;
    log.occurredAt = occurredAt;
    return log;
  }
}
