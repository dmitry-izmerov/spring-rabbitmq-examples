package ru.demi.rabbitmq.simple_queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("simple_queue")
@Configuration
public class Config {
    @Bean
    Queue getQueue() {
        return new Queue("simple");
    }

    @Profile("receiver")
    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Profile("sender")
    @Bean
    Sender sender() {
        return new Sender();
    }
}
