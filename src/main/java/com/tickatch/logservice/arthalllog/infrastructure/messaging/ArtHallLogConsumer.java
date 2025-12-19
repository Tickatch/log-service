package com.tickatch.logservice.arthalllog.infrastructure.messaging;

import com.tickatch.logservice.arthalllog.domain.ArtHallLog;
import com.tickatch.logservice.arthalllog.domain.event.ArtHallLogEvent;
import com.tickatch.logservice.arthalllog.domain.repository.ArtHallLogRepository;
import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtHallLogConsumer {

  private final ArtHallLogRepository artHallLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_ARTHALL_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(ArtHallLogEvent event) {
    try {
      log.debug("Consuming arthall log event: {}", event);

      ArtHallLog log =
          ArtHallLog.create(
              event.eventId(),
              event.domainType(), // ART_HALL | STAGE
              event.domainId(), // artHallId or stageId
              event.actionType(), // ACTIVATED | INACTIVATED | DELETED
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      artHallLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save arthall log: {}", event, e);
      throw e; // DLQ로 전송되도록
    }
  }
}
