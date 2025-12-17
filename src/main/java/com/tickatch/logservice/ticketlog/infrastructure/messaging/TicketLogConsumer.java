package com.tickatch.logservice.ticketlog.infrastructure.messaging;

import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import com.tickatch.logservice.ticketlog.domain.TicketLog;
import com.tickatch.logservice.ticketlog.domain.event.TicketEvent;
import com.tickatch.logservice.ticketlog.domain.repository.TicketLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketLogConsumer {

  private final TicketLogRepository ticketLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_TICKET_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(TicketEvent event) {
    try {
      log.debug("Consuming ticket log: {}", event);

      TicketLog log =
          TicketLog.create(
              event.eventId(),
              event.ticketId(),
              event.receiveMethod(),
              event.actionType(),
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      ticketLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save ticket log: {}", event, e);
      throw e; // DLQ로 전송
    }
  }
}
