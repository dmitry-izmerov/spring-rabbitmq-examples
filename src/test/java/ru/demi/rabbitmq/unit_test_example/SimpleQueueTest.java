package ru.demi.rabbitmq.unit_test_example;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.amqp.rabbit.test.mockito.LatchCountDownAndCallRealMethodAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.demi.rabbitmq._01_simple_queue.Receiver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static ru.demi.rabbitmq._01_simple_queue.Config.RECEIVER_PROFILE;
import static ru.demi.rabbitmq._01_simple_queue.Config.SIMPLE_QUEUE_PROFILE;
import static ru.demi.rabbitmq._01_simple_queue.Receiver.SIMPLE_QUEUE_LISTENER_ID;

@ActiveProfiles({SIMPLE_QUEUE_PROFILE, RECEIVER_PROFILE})
@SpringBootTest
@SpringJUnitConfig
class SimpleQueueTest {

    @Autowired
    private TestRabbitTemplate template;

    @Autowired
    private Queue queue;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    void shouldSendAndReceiveMessage() throws InterruptedException {
        LatchCountDownAndCallRealMethodAnswer answer = new LatchCountDownAndCallRealMethodAnswer(1);
        Receiver listener = harness.getSpy(SIMPLE_QUEUE_LISTENER_ID);
        assertNotNull(listener);
        String message = "message";
        doAnswer(answer)
            .when(listener).receive(message);

        template.convertAndSend(queue.getName(), message);

        assertTrue(answer.getLatch().await(1, TimeUnit.SECONDS));
        verify(listener).receive(message);
    }
}