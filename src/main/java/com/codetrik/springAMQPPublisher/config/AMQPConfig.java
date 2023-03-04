package com.codetrik.springAMQPPublisher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class AMQPConfig {

    //Setup RabbitTemplate with Retry capability
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, RetryTemplate retryTemplate) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setRetryTemplate(retryTemplate);
        return template;
    }

    //Create a Queue
    @Bean("just-queue")
    public Queue createQueue(@Value("${com.codetrik.amqp.queue}") String name){
        return new Queue(name,false);
    }

    @Bean("rpc-queue")
    public Queue createRpcQueue(@Value("${com.codetrik.amqp.rpc-queue}") String name){
        return new Queue(name,false);
    }

    @Bean("reply-to-queue")
    public Queue createReplyToQueue(@Value("${com.codetrik.amqp.reply-to-queue}") String name){
        return new Queue(name,false);
    }

    @Bean("rpc-direct")
    public DirectExchange directExchange(@Value("${com.codetrik.amqp.direct-exchange}") String name){
        return new DirectExchange(name,false,false);
    }

    @Bean
    public Binding rpcQueueBindToDirectExchange(@Qualifier("rpc-queue") Queue rpcQueue,
                                                @Qualifier("rpc-direct") DirectExchange rpcDirectExchange,
                                                @Value("${com.codetrik.amqp.router}") String routeKey){
        return BindingBuilder.bind(rpcQueue).to(rpcDirectExchange).with(routeKey);
    }

    //Set up a RetryTemplate Bean, this is intended to add retry capability to RabbitTemplate
    @Bean
    public RetryTemplate retry(){
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

    //Setup ConnectionFactory, add a connectionListener to it
    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.host}") String host,
                                               @Value("${spring.rabbitmq.port}")int port,
                                               @Value("${spring.rabbitmq.username}") String username,
                                               @Value("${spring.rabbitmq.password}") String password,
                                               @Value("${spring.rabbitmq.virtual-host}") String virtualPort,
                                               ConnectionListener connectionListener){
        var rabbitConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setHost(host);
        rabbitConnectionFactory.setPort(port);
        rabbitConnectionFactory.setUsername(username);
        rabbitConnectionFactory.setPassword(password);
        rabbitConnectionFactory.setVirtualHost(virtualPort);
        PooledChannelConnectionFactory connectionFactory = new PooledChannelConnectionFactory(rabbitConnectionFactory);
        connectionFactory.addConnectionListener(connectionListener);
        connectionFactory.setPoolConfigurer((pool, tx) -> {
            if (tx) {
                // configure the transactional pool
            }
            else {
                // configure the non-transactional pool
            }
        });
        return connectionFactory;
    }
}
