package ru.demi.rabbitmq._06_rpc_request;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Base64;

public class Server {

    @RabbitListener(queues = "rpc_requests")
    public String encode(String value) {
        System.out.println("Received request for value: " + value);
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
