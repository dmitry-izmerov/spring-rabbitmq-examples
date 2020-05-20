package ru.demi.rabbitmq._04_routing;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;

    private AtomicInteger index = new AtomicInteger(-1);
    private String[] keys = new String[] { "info", "warning", "error" };


    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    void send() {
        index.getAndUpdate((int v) -> ++v % keys.length);
        String key = keys[index.get()];
        String message = "Some log with level " + key;
        this.rabbitTemplate.convertAndSend(directExchange.getName(), key, message);
        System.out.printf("Message: %s is sent.%n", message);
    }
}
