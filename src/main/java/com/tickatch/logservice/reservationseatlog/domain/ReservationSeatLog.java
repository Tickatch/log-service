package com.tickatch.logservice.reservationseatlog.domain;

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
@Table(name = "p_reservation_seat_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSeatLog {

  @Id
  @Column(name = "id", nullable = false)
  private UUID reservationSeatLogId;

  @Column(name = "reservation_seat_id", nullable = false)
  private Long reservationSeatId;

  @Column(name = "seat_number", nullable = false)
  private String seatNumber;

  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType;

  @Column(name = "actor_type", nullable = false, length = 20)
  private String actorType;

  @Column(name = "actor_user_id")
  private UUID actorUserId;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  public static ReservationSeatLog create(
      UUID reservationSeatLogId,
      Long reservationSeatId,
      String seatNumber,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {
    ReservationSeatLog log = new ReservationSeatLog();
    log.reservationSeatLogId = reservationSeatLogId;
    log.reservationSeatId = reservationSeatId;
    log.seatNumber = seatNumber;
    log.actionType = actionType;
    log.actorType = actorType;
    log.actorUserId = actorUserId;
    log.occurredAt = occurredAt;
    return log;
  }
}
