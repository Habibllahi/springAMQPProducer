package com.codetrik.springAMQPPublisher.schedular;

import com.codetrik.springAMQPPublisher.amqp.SimpleProducer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Getter
@Setter
public class Publish {
    private final String exchange;
    private final SimpleProducer simpleProducer;
    private final String queue;
    Publish(@Value("${com.codetrik.amqp.default-exchange:}") String exchange, SimpleProducer simpleProducer,
            @Value("${com.codetrik.amqp.queue}") String queue){

        this.exchange = exchange;
        this.simpleProducer = simpleProducer;
        this.queue = queue;
    }

    @Scheduled(fixedDelay = 2L, timeUnit = TimeUnit.SECONDS)
    @Async
    public void run(){
        simpleProducer.send(exchange,queue,"Hello AMQP");
    }

}
