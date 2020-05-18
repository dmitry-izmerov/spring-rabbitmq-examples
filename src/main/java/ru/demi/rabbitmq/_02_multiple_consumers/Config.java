package ru.demi.rabbitmq._02_multiple_consumers;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("multiple_consumers")
@Configuration
public class Config {
    @Bean
    Queue queue() {
        return new Queue("multiple_consumers");
    }

    @Profile("receiver")
    @Bean
    Receiver receiver1() {
        return new Receiver(1);
    }

    @Profile("receiver")
    @Bean
    Receiver receiver2() {
        return new Receiver(2);
    }

    @Profile("sender")
    @Bean
    Sender sender() {
        return new Sender();
    }
}
