package com.codetrik.springAMQPPublisher.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    //Create a Queue
    @Bean
    public Queue createQueue(@Value("${com.codetrik.amqp.queue}") String name){
        return new Queue(name,false);
    }
}
