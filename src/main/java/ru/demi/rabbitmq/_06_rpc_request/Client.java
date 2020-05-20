package ru.demi.rabbitmq._06_rpc_request;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicLong;

public class Client {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;

    private AtomicLong counter = new AtomicLong(0);

    @Scheduled(fixedRate = 1000)
    public void send() {
        String name = directExchange.getName();
        String message = "message" + counter.incrementAndGet();
        String response = (String) rabbitTemplate.convertSendAndReceive(name, "rpc", message);
        System.out.println("Response: " + response);
    }
}
