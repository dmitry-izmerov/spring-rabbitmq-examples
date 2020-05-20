package ru.demi.rabbitmq._06_rpc_request;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("rpc")
@Configuration
public class Config {

    @Profile("client")
    private static class ClientConfig {

        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("rpc");
        }

        @Bean
        public Client client() {
            return new Client();
        }

    }

    @Profile("server")
    private static class ServerConfig {

        @Bean
        public Queue requests() {
            return new Queue("rpc_requests");
        }

        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("rpc");
        }

        @Bean
        public Binding binding(DirectExchange directExchange, Queue requests) {
            return BindingBuilder.bind(requests)
                .to(directExchange)
                .with("rpc");
        }

        @Bean
        public Server server() {
            return new Server();
        }
    }
}