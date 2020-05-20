package ru.demi.rabbitmq._04_routing;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "#{nonErrorQueue.name}")
    public void receive1(String message) {
        System.out.printf("Consumer for not error messages received: %s.%n", message);
    }

    @RabbitListener(queues = "#{errorQueue.name}")
    public void receive2(String message) {
        System.out.printf("Consumer for error messages received: %s.%n", message);
    }
}
