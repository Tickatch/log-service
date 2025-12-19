package com.tickatch.logservice.productlog.infrastructure.messaging;

import com.tickatch.logservice.global.config.rabbitmq.RabbitMQConfig;
import com.tickatch.logservice.productlog.domain.ProductLog;
import com.tickatch.logservice.productlog.domain.event.ProductEvent;
import com.tickatch.logservice.productlog.domain.repository.ProductLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductLogConsumer {

  private final ProductLogRepository productLogRepository;

  @RabbitListener(
      queues = RabbitMQConfig.QUEUE_PRODUCT_LOG,
      containerFactory = "rabbitListenerContainerFactory")
  @Transactional
  public void consume(ProductEvent event) {
    try {
      log.debug("Consuming product log: {}", event);

      ProductLog log =
          ProductLog.create(
              event.eventId(),
              event.productId(),
              event.actionType(),
              event.actorType(),
              event.actorUserId(),
              event.occurredAt());

      productLogRepository.save(log);

    } catch (Exception e) {
      log.error("Failed to save product log: {}", event, e);
      throw e; // DLQ로 전송되도록
    }
  }
}
