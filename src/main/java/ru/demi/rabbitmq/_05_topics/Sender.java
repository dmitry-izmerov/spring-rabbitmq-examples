package ru.demi.rabbitmq._05_topics;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicExchange topicExchange;

    private AtomicInteger index = new AtomicInteger(-1);
    private String[] keys = new String[] {
        "lada.new",
        "gazel.used",
        "uaz.crashed",
        "lada.used",
        "gazel.crashed",
        "uaz.new",
        "lada.crashed",
        "gazel.new",
    };


    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    void send() {
        index.getAndUpdate((int v) -> ++v % keys.length);
        String key = keys[index.get()];
        String message = "Car info: " + key;
        this.rabbitTemplate.convertAndSend(topicExchange.getName(), key, message);
        System.out.printf("Message: %s is sent.%n", message);
    }
}
