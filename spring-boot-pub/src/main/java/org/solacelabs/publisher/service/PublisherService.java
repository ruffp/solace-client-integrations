package org.solacelabs.publisher.service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solacelabs.publisher.configuration.PublisherProperties;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class);

    private final PublisherProperties publisherProperties;
    private final JmsTemplate jmsTemplate;

    public PublisherService(PublisherProperties publisherProperties, JmsTemplate jmsTemplate) {
        this.publisherProperties = publisherProperties;
        this.jmsTemplate = jmsTemplate;
    }

    @PostConstruct
    private void customizeJmsTemplate() {
        // Update the jmsTemplate's connection factory to cache the connection
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
        jmsTemplate.setConnectionFactory(ccf);
    }

    public void publishMessage(String channel, String body, boolean isTopic) {
        jmsTemplate.setPubSubDomain(isTopic);
        if (StringUtils.isBlank(channel)) {
            channel = publisherProperties.topicName();
            jmsTemplate.setPubSubDomain(true);
        }
        jmsTemplate.convertAndSend(channel, body);
        LOGGER.info("Message sent");
    }
}
