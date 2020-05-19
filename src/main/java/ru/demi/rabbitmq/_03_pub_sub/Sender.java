package ru.demi.rabbitmq._03_pub_sub;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicLong;

public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private FanoutExchange fanoutExchange;

    private AtomicLong counter = new AtomicLong(0);

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    void send() {
        String message = "Message - " + counter.incrementAndGet();
        this.rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", message);
        System.out.printf("Message: %s is sent.%n", message);
    }
}
