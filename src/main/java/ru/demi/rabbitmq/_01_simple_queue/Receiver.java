package ru.demi.rabbitmq._01_simple_queue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static ru.demi.rabbitmq._01_simple_queue.Config.SIMPLE_QUEUE_NAME;

@RabbitListener(id = Receiver.SIMPLE_QUEUE_LISTENER_ID, queues = SIMPLE_QUEUE_NAME)
public class Receiver {

    public static final String SIMPLE_QUEUE_LISTENER_ID = "simple_listener";

    @RabbitHandler
    public void receive(String message) {
        System.out.println("Received - <" + message + ">");
    }

}