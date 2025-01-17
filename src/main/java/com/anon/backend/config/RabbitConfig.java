package com.anon.backend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
  @Bean("Exchange")
  public Exchange exchange() {
    return ExchangeBuilder.directExchange("amq.direct").build();
  }

  @Bean("Queue")
  public Queue queue() {
    return QueueBuilder.nonDurable("tag").build();
  }

  @Bean("binding")
  public Binding binding(
      @Qualifier("Exchange") Exchange exchange, @Qualifier("Queue") Queue queue) {
    return BindingBuilder.bind(queue).to(exchange).with("tag.create").noargs();
  }

  @Bean
  public MessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
