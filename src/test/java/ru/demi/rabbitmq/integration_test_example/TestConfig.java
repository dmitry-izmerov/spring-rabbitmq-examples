package ru.demi.rabbitmq.integration_test_example;

import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Funny fact is that when I put this config in test class as inner static class,
 * auto configuration for beans doesn't work.
 */
@Profile(TestConfig.INTEGRATION_TEST)
@RabbitListenerTest(capture = true, spy = false)
@Configuration
public class TestConfig {
    public static final String INTEGRATION_TEST = "integration-test";
}
