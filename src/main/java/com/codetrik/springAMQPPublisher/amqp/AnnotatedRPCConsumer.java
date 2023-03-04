package com.codetrik.springAMQPPublisher.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AnnotatedRPCConsumer {
    @Value("${com.codetrik.amqp.correlationId}")
    private String correlationId;
    @RabbitListener(queues = {"${com.codetrik.amqp.reply-to-queue}"})
    public void replyProcessing(Message message){
        if(message.getMessageProperties().getCorrelationId().equals(correlationId)){
            System.out.println(new String(message.getBody()));
        }
    }
}
