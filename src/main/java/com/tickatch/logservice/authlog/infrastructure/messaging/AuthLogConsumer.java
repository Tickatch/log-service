package com.tickatch.logservice.authlog.infrastructure.messaging;

import com.tickatch.logservice.authlog.domain.AuthLog;
import com.tickatch.logservice.authlog.domain.event.AuthEvent;
import com.tickatch.logservice.authlog.domain.repository.AuthLogRepository;
import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthLogConsumer {

  private final AuthLogRepository authLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_AUTH_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(AuthEvent event) {
    try {
      log.debug("Consuming auth log event: {}", event);

      AuthLog log =
          AuthLog.create(
              event.eventId(),
              event.actionType(),
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      authLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save auth log: {}", event, e);
      throw e; // DLQ
    }
  }
}
