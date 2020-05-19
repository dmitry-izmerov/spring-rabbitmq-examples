package ru.demi.rabbitmq._03_pub_sub;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Receiver {

    // TODO looks bad, try to pass value from object itself
    @RabbitListener(queues = "#{anonQueue1.name}")
    public void receive1(String message) {
        receive(message, 1);
    }

    @RabbitListener(queues = "#{anonQueue2.name}")
    public void receive2(String message) {
        receive(message, 2);
    }

    private void receive(String message, int number) {
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
