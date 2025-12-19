package com.tickatch.logservice.userlog.infrastructure.messaging;

import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import com.tickatch.logservice.userlog.domain.UserLog;
import com.tickatch.logservice.userlog.domain.event.UserEvent;
import com.tickatch.logservice.userlog.domain.repository.UserLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLogConsumer {

  private final UserLogRepository userLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_USER_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(UserEvent event) {
    try {
      log.debug("Consuming user log event: {}", event);

      UserLog log =
          UserLog.create(
              event.eventId(),
              event.userId(),
              event.actionType(),
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      userLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save user log: {}", event, e);
      throw e; // DLQ 전송
    }
  }
}
