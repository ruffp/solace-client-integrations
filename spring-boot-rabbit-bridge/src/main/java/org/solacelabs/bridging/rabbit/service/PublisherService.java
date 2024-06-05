package org.solacelabs.bridging.rabbit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solacelabs.bridging.rabbit.configuration.RabbitBridgeProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class);

    private final RabbitBridgeProperties rabbitBridgeProperties;
    private final JmsTemplate jmsTemplate;

    public PublisherService(RabbitBridgeProperties rabbitBridgeProperties, JmsTemplate jmsTemplate) {
        this.rabbitBridgeProperties = rabbitBridgeProperties;
        this.jmsTemplate = jmsTemplate;
    }

    public void publishMessage(String channel, String body, boolean isTopic){
        jmsTemplate.setPubSubDomain(isTopic);
        jmsTemplate.convertAndSend(channel, body);
        LOGGER.info("Message sent");
    }
}
