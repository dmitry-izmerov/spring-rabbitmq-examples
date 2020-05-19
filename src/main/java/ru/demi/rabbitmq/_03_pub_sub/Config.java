package ru.demi.rabbitmq._03_pub_sub;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("pub_sub")
@Configuration
public class Config {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Queue anonQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue anonQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(FanoutExchange fanoutExchange, Queue anonQueue1) {
            return BindingBuilder.bind(anonQueue1).to(fanoutExchange);
        }

        @Bean
        public Binding binding2(FanoutExchange fanoutExchange, Queue anonQueue2) {
            return BindingBuilder.bind(anonQueue2).to(fanoutExchange);
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