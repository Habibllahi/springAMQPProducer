package com.codetrik.springAMQPPublisher;

import com.codetrik.springAMQPPublisher.properties.AMQPProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(value = {AMQPProperties.class})
@EnableAsync
@EnableScheduling
public class SpringAmqpPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAmqpPublisherApplication.class, args);
	}

}
