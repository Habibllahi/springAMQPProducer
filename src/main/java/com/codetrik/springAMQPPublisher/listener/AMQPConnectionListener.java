package com.codetrik.springAMQPPublisher.listener;

import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class AMQPConnectionListener implements ConnectionListener {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public void onCreate(Connection connection) {
        logger.info("[INFO] publisher made a new connection ");
    }

    @Override
    public void onClose(Connection connection) {
        ConnectionListener.super.onClose(connection);
        logger.info("[INFO] publisher closed a connection ");
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {
        ConnectionListener.super.onShutDown(signal);
        logger.info("[INFO] publisher experience connection shutdown ");
    }

    @Override
    public void onFailed(Exception exception) {
        ConnectionListener.super.onFailed(exception);
        logger.info("[INFO] publisher experience connection failure ");
    }
}
