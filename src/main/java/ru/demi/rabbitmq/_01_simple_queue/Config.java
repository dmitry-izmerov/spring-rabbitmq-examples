package ru.demi.rabbitmq._01_simple_queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(Config.SIMPLE_QUEUE_PROFILE)
@Configuration
public class Config {

    public static final String SIMPLE_QUEUE_PROFILE = "simple_queue";
    public static final String RECEIVER_PROFILE = "receiver";
    public static final String SIMPLE_QUEUE_NAME = "simple";

    @Bean
    Queue queue() {
        return new Queue(SIMPLE_QUEUE_NAME);
    }

    @Profile(RECEIVER_PROFILE)
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
