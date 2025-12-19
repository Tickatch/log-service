package com.tickatch.logservice.reservationlog.infrastructure.messaging;

import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import com.tickatch.logservice.reservationlog.domain.ReservationLog;
import com.tickatch.logservice.reservationlog.domain.event.ReservationEvent;
import com.tickatch.logservice.reservationlog.domain.repository.ReservationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationLogConsumer {

  private final ReservationLogRepository reservationLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_RESERVATION_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(ReservationEvent event) {
    try {
      log.debug("Consuming reservation log: {}", event);

      ReservationLog log =
          ReservationLog.create(
              event.eventId(),
              event.reservationId(),
              event.reservationNumber(),
              event.actionType(),
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      reservationLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save reservation log: {}", event, e);
      throw e; // DLQ로 전송
    }
  }
}
