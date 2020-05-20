package ru.demi.rabbitmq._05_topics;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "#{crashedCars.name}")
    public void receiveInfoAboutCrashedCars(String message) {
        System.out.printf("Info about crashed cars: %s.%n", message);
    }

    @RabbitListener(queues = "#{ladaCars.name}")
    public void receiveInfoAboutVazCars(String message) {
        System.out.printf("Info about lada cars: %s.%n", message);
    }
}
