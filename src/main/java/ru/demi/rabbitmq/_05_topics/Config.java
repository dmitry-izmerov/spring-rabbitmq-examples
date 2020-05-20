package ru.demi.rabbitmq._05_topics;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("topics")
@Configuration
public class Config {

    // key pattern = <car>.<state>
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topics");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Queue crashedCars() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue ladaCars() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindingInfo(TopicExchange topicExchange, Queue crashedCars) {
            return BindingBuilder
                .bind(crashedCars)
                .to(topicExchange)
                .with("*.crashed");
        }

        @Bean
        public Binding bindingWarning(TopicExchange topicExchange, Queue ladaCars) {
            return BindingBuilder
                .bind(ladaCars)
                .to(topicExchange)
                .with("lada.*");
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