package ru.demi.rabbitmq._02_multiple_consumers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RabbitListener(queues = "multiple_consumers")
public class Receiver {

    private int number;

    public Receiver(int number) {
        this.number = number;
    }

    @RabbitHandler
    public void receive(String message) {
        System.out.printf("Consumer №%d received: %s.%n", number, message);
        try {
            int timeout = ThreadLocalRandom.current().nextInt(2, 6 + 1);
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Consumer №%d processed message.%n", number);
    }

}