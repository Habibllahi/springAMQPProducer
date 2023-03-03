package com.codetrik.springAMQPPublisher.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class AMQPConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, RetryTemplate retryTemplate) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setRetryTemplate(retryTemplate);
        return template;
    }

    //Create a Queue
    @Bean
    public Queue createQueue(@Value("${com.codetrik.amqp.queue}") String name){
        return new Queue(name,false);
    }

    //Set up a RetryTemplate Bean, this is intended to add retry capability to RabbitTemplate
    @Bean RetryTemplate retry(){
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
