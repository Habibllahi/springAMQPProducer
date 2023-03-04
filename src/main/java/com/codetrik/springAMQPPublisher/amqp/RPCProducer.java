package com.codetrik.springAMQPPublisher.amqp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class RPCProducer {
    private final RabbitTemplate rabbitTemplate;
    public void sendAndReceive(String exchange, String routingKey, String message, String replyTo, String correlationId){
        var props = MessagePropertiesBuilder.newInstance().setReplyTo(replyTo).setCorrelationId("correlationId")
                .build();
        var msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8)).andProperties(props).build();
        rabbitTemplate.sendAndReceive(exchange,routingKey,msg);
    }
}
