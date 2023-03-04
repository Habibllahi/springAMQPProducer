package com.codetrik.springAMQPPublisher.schedular;

import com.codetrik.springAMQPPublisher.amqp.RPCProducer;
import com.codetrik.springAMQPPublisher.amqp.SimpleProducer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class Publish {
    private final SimpleProducer simpleProducer;
    private final RPCProducer rpcProducer;
    @Value("${com.codetrik.amqp.default-exchange:}")
    private String exchange;
    @Value("${com.codetrik.amqp.queue}")
    private String queue;
    @Value("${com.codetrik.amqp.direct-exchange}")
    private String rpcDirect;
    @Value("${com.codetrik.amqp.router}")
    private String routeKey;
    @Value("${com.codetrik.amqp.correlationId}")
    private String correlationId;

    @Scheduled(fixedDelay = 2L, timeUnit = TimeUnit.SECONDS)
    @Async
    public void run1(){
        simpleProducer.send(exchange,queue,"Hello AMQP");
    }

    @Scheduled(fixedDelay = 2L, timeUnit = TimeUnit.SECONDS)
    @Async
    public void run2(){
        rpcProducer.send(rpcDirect,routeKey,"Hello AMQP RPC",correlationId);
    }

}
