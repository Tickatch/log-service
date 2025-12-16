package com.tickatch.logservice.global.config.rabbitmq;

import io.github.tickatch.common.util.JsonUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  /* =========================
   * Exchange / Queue / Routing
   * ========================= */
  public static final String LOG_EXCHANGE = "tickatch.log";

  public static final String QUEUE_RESERVATION_SEAT_LOG = "tickatch.reservation-seat.log.queue";

  public static final String ROUTING_KEY_RESERVATION_SEAT_LOG = "reservation-seat.log";

  /* =========================
   * Exchange
   * ========================= */
  @Bean
  public TopicExchange logExchange() {
    return ExchangeBuilder.topicExchange(LOG_EXCHANGE).durable(true).build();
  }

  /* =========================
   * Queue
   * ========================= */
  @Bean
  public Queue reservationSeatLogQueue() {
    return QueueBuilder.durable(QUEUE_RESERVATION_SEAT_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_RESERVATION_SEAT_LOG)
        .build();
  }

  /* =========================
   * Binding
   * ========================= */
  @Bean
  public Binding reservationSeatLogBinding(
      Queue reservationSeatLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(reservationSeatLogQueue)
        .to(logExchange)
        .with(ROUTING_KEY_RESERVATION_SEAT_LOG);
  }

  /* =========================
   * Dead Letter
   * ========================= */
  @Bean
  public TopicExchange deadLetterExchange() {
    return ExchangeBuilder.topicExchange(LOG_EXCHANGE + ".dlx").durable(true).build();
  }

  @Bean
  public Queue reservationSeatLogDlq() {
    return QueueBuilder.durable(QUEUE_RESERVATION_SEAT_LOG + ".dlq").build();
  }

  @Bean
  public Binding reservationSeatLogDlqBinding(
      Queue reservationSeatLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(reservationSeatLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_RESERVATION_SEAT_LOG);
  }

  /* =========================
   * Message Converter
   * ========================= */
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter(JsonUtils.getObjectMapper());
  }

  /* =========================
   * Listener Factory
   * ========================= */
  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(jsonMessageConverter);
    factory.setDefaultRequeueRejected(false); // 실패 시 DLQ
    factory.setPrefetchCount(10);
    return factory;
  }
}
