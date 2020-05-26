package ru.demi.rabbitmq.integration_test_example;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.demi.rabbitmq._01_simple_queue.Config.*;
import static ru.demi.rabbitmq._01_simple_queue.Receiver.SIMPLE_QUEUE_LISTENER_ID;
import static ru.demi.rabbitmq.integration_test_example.TestConfig.INTEGRATION_TEST;

/**
 * Attention!
 * You need docker for running this test.
 */
@Testcontainers
@ActiveProfiles({SIMPLE_QUEUE_PROFILE, RECEIVER_PROFILE, INTEGRATION_TEST})
@SpringBootTest
@SpringJUnitConfig
public class SimpleTest {

    @Container
    static RabbitMQContainer rabbitmq = new RabbitMQContainer();

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        rabbitmq.start();
        registry.add("spring.rabbitmq.host", rabbitmq::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", rabbitmq::getFirstMappedPort);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    public void shouldSendAndReceiveMessage() throws InterruptedException {
        String message = "message";
        rabbitTemplate.convertAndSend(SIMPLE_QUEUE_NAME, message);

        InvocationData data = harness.getNextInvocationDataFor(SIMPLE_QUEUE_LISTENER_ID, 1, TimeUnit.SECONDS);
        assertThat(data.getArguments()[0], equalTo(message));
    }
}
