package com.tickatch.logservice.paymentlog.infrastructure.messaging;

import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import com.tickatch.logservice.paymentlog.domain.PaymentLog;
import com.tickatch.logservice.paymentlog.domain.event.PaymentEvent;
import com.tickatch.logservice.paymentlog.domain.repository.PaymentLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentLogConsumer {

  private final PaymentLogRepository paymentLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_PAYMENT_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(PaymentEvent event) {
    try {
      log.debug("Consuming payment log: {}", event);

      PaymentLog log =
          PaymentLog.create(
              event.eventId(),
              event.paymentId(),
              event.method(),
              event.retryCount(),
              event.actionType(),
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      paymentLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save payment log: {}", event, e);
      throw e; // DLQ로 전송
    }
  }
}
