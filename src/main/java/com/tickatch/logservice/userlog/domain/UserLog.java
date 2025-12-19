package com.tickatch.logservice.userlog.domain;

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
@Table(name = "p_user_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLog {

  @Id
  @Column(name = "id", nullable = false)
  private UUID userLogId;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType;

  @Column(name = "actor_type", nullable = false, length = 20)
  private String actorType;

  @Column(name = "actor_user_id")
  private UUID actorUserId;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  public static UserLog create(
      UUID userLogId,
      UUID userId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {

    UserLog log = new UserLog();
    log.userLogId = userLogId;
    log.userId = userId;
    log.actionType = actionType;
    log.actorType = actorType;
    log.actorUserId = actorUserId;
    log.occurredAt = occurredAt;
    return log;
  }
}
