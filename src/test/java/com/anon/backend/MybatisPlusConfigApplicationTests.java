package com.anon.backend;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusConfigApplicationTests {
  @Resource RabbitTemplate template;

  @Test
  void contextLoads() {
    template.convertAndSend("amq.direct", "tag-key", "Hello World!");
  }
}
