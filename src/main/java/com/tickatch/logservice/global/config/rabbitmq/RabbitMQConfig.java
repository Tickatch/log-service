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

  // 예매 좌석
  public static final String QUEUE_RESERVATION_SEAT_LOG = "tickatch.reservation-seat.log.queue";
  public static final String ROUTING_KEY_RESERVATION_SEAT_LOG = "reservation-seat.log";

  // 아트홀
  public static final String QUEUE_ARTHALL_LOG = "tickatch.arthall.log.queue";
  public static final String ROUTING_KEY_ARTHALL_LOG = "arthall.log";

  // 상품
  public static final String QUEUE_PRODUCT_LOG = "tickatch.product.log.queue";
  public static final String ROUTING_KEY_PRODUCT_LOG = "product.log";

  // 예매
  public static final String QUEUE_RESERVATION_LOG = "tickatch.reservation.log.queue";
  public static final String ROUTING_KEY_RESERVATION_LOG = "reservation.log";

  // 티켓
  public static final String QUEUE_TICKET_LOG = "tickatch.ticket.log.queue";
  public static final String ROUTING_KEY_TICKET_LOG = "ticket.log";

  // 결제
  public static final String QUEUE_PAYMENT_LOG = "tickatch.payment.log.queue";
  public static final String ROUTING_KEY_PAYMENT_LOG = "payment.log";

  // 유저
  public static final String QUEUE_USER_LOG = "tickatch.user.log.queue";
  public static final String ROUTING_KEY_USER_LOG = "user.log";

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
  // 예매 좌석
  @Bean
  public Queue reservationSeatLogQueue() {
    return QueueBuilder.durable(QUEUE_RESERVATION_SEAT_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_RESERVATION_SEAT_LOG)
        .build();
  }

  // 아트홀
  @Bean
  public Queue artHallLogQueue() {
    return QueueBuilder.durable(QUEUE_ARTHALL_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_ARTHALL_LOG)
        .build();
  }

  // 상품
  @Bean
  public Queue productLogQueue() {
    return QueueBuilder.durable(QUEUE_PRODUCT_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_PRODUCT_LOG)
        .build();
  }

  // 예매
  @Bean
  public Queue reservationLogQueue() {
    return QueueBuilder.durable(QUEUE_RESERVATION_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_RESERVATION_LOG)
        .build();
  }

  // 티켓
  @Bean
  public Queue ticketLogQueue() {
    return QueueBuilder.durable(QUEUE_TICKET_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_TICKET_LOG)
        .build();
  }

  // 결제
  @Bean
  public Queue paymentLogQueue() {
    return QueueBuilder.durable(QUEUE_PAYMENT_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_PAYMENT_LOG)
        .build();
  }

  // 유저
  @Bean
  public Queue userLogQueue() {
    return QueueBuilder.durable(QUEUE_USER_LOG)
        .withArgument("x-dead-letter-exchange", LOG_EXCHANGE + ".dlx")
        .withArgument("x-dead-letter-routing-key", "dlq." + ROUTING_KEY_USER_LOG)
        .build();
  }

  /* =========================
   * Binding
   * ========================= */
  // 예매 좌석
  @Bean
  public Binding reservationSeatLogBinding(
      Queue reservationSeatLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(reservationSeatLogQueue)
        .to(logExchange)
        .with(ROUTING_KEY_RESERVATION_SEAT_LOG);
  }

  // 아트홀
  @Bean
  public Binding artHallLogBinding(Queue artHallLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(artHallLogQueue).to(logExchange).with(ROUTING_KEY_ARTHALL_LOG);
  }

  // 상품
  @Bean
  public Binding productLogBinding(Queue productLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(productLogQueue).to(logExchange).with(ROUTING_KEY_PRODUCT_LOG);
  }

  // 예매
  @Bean
  public Binding reservationLogBinding(Queue reservationLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(reservationLogQueue)
        .to(logExchange)
        .with(ROUTING_KEY_RESERVATION_LOG);
  }

  // 티켓
  @Bean
  public Binding ticketLogBinding(Queue ticketLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(ticketLogQueue).to(logExchange).with(ROUTING_KEY_TICKET_LOG);
  }

  // 결제
  @Bean
  public Binding paymentLogBinding(Queue paymentLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(paymentLogQueue).to(logExchange).with(ROUTING_KEY_PAYMENT_LOG);
  }

  // 유저
  @Bean
  public Binding userLogBinding(Queue userLogQueue, TopicExchange logExchange) {
    return BindingBuilder.bind(userLogQueue).to(logExchange).with(ROUTING_KEY_USER_LOG);
  }

  /* =========================
   * Dead Letter
   * ========================= */
  @Bean
  public TopicExchange deadLetterExchange() {
    return ExchangeBuilder.topicExchange(LOG_EXCHANGE + ".dlx").durable(true).build();
  }

  // 예매 좌석
  @Bean
  public Queue reservationSeatLogDlq() {
    return QueueBuilder.durable(QUEUE_RESERVATION_SEAT_LOG + ".dlq").build();
  }

  // 아트홀
  @Bean
  public Queue artHallLogDlq() {
    return QueueBuilder.durable(QUEUE_ARTHALL_LOG + ".dlq").build();
  }

  // 상품
  @Bean
  public Queue productLogDlq() {
    return QueueBuilder.durable(QUEUE_PRODUCT_LOG + ".dlq").build();
  }

  // 예매
  @Bean
  public Queue reservationLogDlq() {
    return QueueBuilder.durable(QUEUE_RESERVATION_LOG + ".dlq").build();
  }

  // 티켓
  @Bean
  public Queue ticketLogDlq() {
    return QueueBuilder.durable(QUEUE_TICKET_LOG + ".dlq").build();
  }

  // 결제
  @Bean
  public Queue paymentLogDlq() {
    return QueueBuilder.durable(QUEUE_PAYMENT_LOG + ".dlq").build();
  }

  // 유저
  @Bean
  public Queue userLogDlq() {
    return QueueBuilder.durable(QUEUE_USER_LOG + ".dlq").build();
  }

  // 예매 좌석
  @Bean
  public Binding reservationSeatLogDlqBinding(
      Queue reservationSeatLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(reservationSeatLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_RESERVATION_SEAT_LOG);
  }

  // 아트홀
  @Bean
  public Binding artHallLogDlqBinding(Queue artHallLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(artHallLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_ARTHALL_LOG);
  }

  // 상품
  @Bean
  public Binding productLogDlqBinding(Queue productLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(productLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_PRODUCT_LOG);
  }

  // 예매
  @Bean
  public Binding reservationLogDlqBinding(
      Queue reservationLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(reservationLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_RESERVATION_LOG);
  }

  // 티켓
  @Bean
  public Binding ticketLogDlqBinding(Queue ticketLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(ticketLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_TICKET_LOG);
  }

  // 결제
  @Bean
  public Binding paymentLogDlqBinding(Queue paymentLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(paymentLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_PAYMENT_LOG);
  }

  // 유저
  @Bean
  public Binding userLogDlqBinding(Queue userLogDlq, TopicExchange deadLetterExchange) {
    return BindingBuilder.bind(userLogDlq)
        .to(deadLetterExchange)
        .with("dlq." + ROUTING_KEY_USER_LOG);
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
