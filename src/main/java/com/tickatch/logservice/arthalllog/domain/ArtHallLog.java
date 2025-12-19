package com.tickatch.logservice.arthalllog.domain;

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
@Table(name = "p_arthall_domain_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtHallLog {

  @Id
  @Column(name = "id", nullable = false)
  private UUID artHallLogId;

  @Column(name = "domain_type", nullable = false, length = 20)
  private String domainType; // ART_HALL | STAGE

  @Column(name = "domain_id", nullable = false)
  private Long domainId; // artHallId or stageId

  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType; // ACTIVATED | INACTIVATED | DELETED

  @Column(name = "actor_type", nullable = false, length = 20)
  private String actorType;

  @Column(name = "actor_user_id")
  private UUID actorUserId;

  @Column(name = "occurred_at", nullable = false)
  private LocalDateTime occurredAt;

  public static ArtHallLog create(
      UUID artHallLogId,
      String domainType,
      Long domainId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {

    ArtHallLog log = new ArtHallLog();
    log.artHallLogId = artHallLogId;
    log.domainType = domainType;
    log.domainId = domainId;
    log.actionType = actionType;
    log.actorType = actorType;
    log.actorUserId = actorUserId;
    log.occurredAt = occurredAt;
    return log;
  }
}
