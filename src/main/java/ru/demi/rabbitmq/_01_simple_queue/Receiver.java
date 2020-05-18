package ru.demi.rabbitmq._01_simple_queue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "simple")
public class Receiver {

    @RabbitHandler
    public void receive(String message) {
        System.out.println("Received - <" + message + ">");
    }

}