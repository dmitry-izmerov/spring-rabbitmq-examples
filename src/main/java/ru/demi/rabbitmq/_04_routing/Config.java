package ru.demi.rabbitmq._04_routing;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("routing")
@Configuration
public class Config {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("routing");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Queue nonErrorQueue() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue errorQueue() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingInfo(DirectExchange directExchange, Queue nonErrorQueue) {
            return BindingBuilder
                .bind(nonErrorQueue)
                .to(directExchange)
                .with("info");
        }

        @Bean
        public Binding bindingWarning(DirectExchange directExchange, Queue nonErrorQueue) {
            return BindingBuilder
                .bind(nonErrorQueue)
                .to(directExchange)
                .with("warning");
        }

        @Bean
        public Binding bindingError(DirectExchange directExchange, Queue errorQueue) {
            return BindingBuilder
                .bind(errorQueue)
                .to(directExchange)
                .with("error");
        }

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}