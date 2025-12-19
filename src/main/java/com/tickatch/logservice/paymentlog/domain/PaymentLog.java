package com.tickatch.logservice.paymentlog.domain;

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
@Table(name = "p_payment_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentLog {

  @Id
  @Column(name = "id", nullable = false)
  private UUID paymentLogId;

  @Column(name = "payment_id", nullable = false)
  private UUID paymentId;

  @Column(name = "method")
  private String method;

  @Column(name = "retry_count", nullable = false)
  private int retryCount;

  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType;

  @Column(name = "actor_type", nullable = false, length = 20)
  private String actorType;

  @Column(name = "actor_user_id")
  private UUID actorUserId;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  public static PaymentLog create(
      UUID paymentLogId,
      UUID paymentId,
      String method,
      int retryCount,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {

    PaymentLog log = new PaymentLog();
    log.paymentLogId = paymentLogId;
    log.paymentId = paymentId;
    log.method = method;
    log.retryCount = retryCount;
    log.actionType = actionType;
    log.actorType = actorType;
    log.actorUserId = actorUserId;
    log.occurredAt = occurredAt;
    return log;
  }
}
