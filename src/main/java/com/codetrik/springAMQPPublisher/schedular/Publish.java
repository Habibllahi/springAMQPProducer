package com.codetrik.springAMQPPublisher.schedular;

import com.codetrik.springAMQPPublisher.amqp.Producer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Getter
@Setter
public class Publish {
    private final String exchange;
    private final Producer producer;
    private final String queue;
    Publish(@Value("${com.codetrik.amqp.default-exchange:}") String exchange, Producer producer,
            @Value("${com.codetrik.amqp.queue}") String queue){

        this.exchange = exchange;
        this.producer = producer;
        this.queue = queue;
    }

    @Scheduled(fixedDelay = 2L, timeUnit = TimeUnit.SECONDS)
    public void run(){
        producer.send(exchange,queue,"Hello AMQP");
    }

}
