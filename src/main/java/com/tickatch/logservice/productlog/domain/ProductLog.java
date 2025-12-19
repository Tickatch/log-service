package com.tickatch.logservice.productlog.domain;

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
@Table(name = "p_product_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLog {

  @Id
  @Column(name = "id", nullable = false)
  private UUID productLogId;

  @Column(name = "product_id", nullable = false)
  private Long productId;

  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType;

  @Column(name = "actor_type", nullable = false, length = 20)
  private String actorType;

  @Column(name = "actor_user_id")
  private UUID actorUserId;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  public static ProductLog create(
      UUID productLogId,
      Long productId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {

    ProductLog log = new ProductLog();
    log.productLogId = productLogId;
    log.productId = productId;
    log.actionType = actionType;
    log.actorType = actorType;
    log.actorUserId = actorUserId;
    log.occurredAt = occurredAt;
    return log;
  }
}
