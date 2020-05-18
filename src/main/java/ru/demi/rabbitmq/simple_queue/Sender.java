package ru.demi.rabbitmq.simple_queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicLong;

public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Queue queue;

    private AtomicLong counter = new AtomicLong(0);

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    void send() {
        this.rabbitTemplate.convertAndSend(queue.getName(), "Hi there! - " + counter.incrementAndGet());
    }
}
