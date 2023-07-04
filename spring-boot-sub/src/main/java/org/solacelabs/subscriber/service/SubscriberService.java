package org.solacelabs.subscriber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solacelabs.subscriber.configuration.SubscriberProperties;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SubscriberService {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberService.class);

    private final SubscriberProperties subscriberProperties;

    public SubscriberService(SubscriberProperties subscriberProperties) {
        this.subscriberProperties = subscriberProperties;
    }

    // Retrieve the name of the queue from the application.properties file
    @JmsListener(destination = "${subscriber.queueName}", concurrency = "2")
    public void handle(Message<?> msg) {
        StringBuilder msgAsStr = new StringBuilder("============= Received \nHeaders:");
        MessageHeaders msgHeaders = msg.getHeaders();
        msgAsStr.append("\nUUID: ")
                .append(msgHeaders.getId())
                .append("\nTimestamp: ")
                .append(msgHeaders.getTimestamp());
        for (Map.Entry<String, Object> item : msgHeaders.entrySet()) {
            msgAsStr.append("\n")
                    .append(item.getKey())
                    .append(": ")
                    .append(item.getValue());
        }
        msgAsStr.append("\nPayload: ")
                .append(msg.getPayload());
        if( subscriberProperties.logEnabled()){
            logger.info("Message consumed: {}", msgAsStr);
        }else{
            logger.info("A message has been consumed");
        }
    }
}
