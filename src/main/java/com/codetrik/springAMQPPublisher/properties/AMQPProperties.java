package com.codetrik.springAMQPPublisher.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.codetrik.amqp")
@Getter
@Setter
public class AMQPProperties {
    private String queue;
    private String defaultExchange;
    private String exchange;

    private String replyToQueue;

}
