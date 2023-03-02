package com.codetrik.springAMQPPublisher.amqp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, String message){
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}
