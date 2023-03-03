package com.codetrik.springAMQPPublisher.amqp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class SimpleProducer {
    private final RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, String message){
        rabbitTemplate.send(exchange,routingKey,new Message(message.getBytes(StandardCharsets.UTF_8)));
    }
}
