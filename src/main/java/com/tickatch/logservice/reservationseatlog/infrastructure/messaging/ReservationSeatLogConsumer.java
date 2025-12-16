package com.tickatch.logservice.reservationseatlog.infrastructure.messaging;

import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import com.tickatch.logservice.reservationseatlog.domain.ReservationSeatLog;
import com.tickatch.logservice.reservationseatlog.domain.event.ReservationSeatActionEvent;
import com.tickatch.logservice.reservationseatlog.domain.repository.ReservationSeatLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationSeatLogConsumer {

  private final ReservationSeatLogRepository reservationSeatLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_RESERVATION_SEAT_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(ReservationSeatActionEvent event) {
    try {
      log.debug("Consuming reservation seat log: {}", event);

      ReservationSeatLog log =
          ReservationSeatLog.create(
              event.eventId(),
              event.reservationSeatId(),
              event.seatNumber(),
              event.actionType(),
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      reservationSeatLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save reservation seat log: {}", event, e);
      throw e; // DLQ로 전송되도록
    }
  }
}
