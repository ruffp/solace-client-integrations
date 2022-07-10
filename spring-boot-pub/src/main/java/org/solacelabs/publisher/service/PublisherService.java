package org.solacelabs.publisher.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solacelabs.publisher.configuration.PublisherProperties;
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

    // Examples of other options to get JmsTemplate in a cloud environment with possibly multiple providers available:
    // Use this to access JmsTemplate of the first service found or look up a specific one by
    // SolaceServiceCredentials
    // @Autowired private SpringSolJmsConnectionFactoryCloudFactory springSolJmsConnectionFactoryCloudFactory;
    // @Autowired private SolaceServiceCredentials solaceServiceCredentials;
    // For backwards compatibility:
    // @Autowired(required=false) private SolaceMessagingInfo solaceMessagingInfo;

    public void publishMessage(String channel, String body, boolean isTopic){
        jmsTemplate.setPubSubDomain(isTopic);
        if( StringUtils.isBlank(channel)){
            channel = publisherProperties.topicName();
            jmsTemplate.setPubSubDomain(true);
        }
        jmsTemplate.convertAndSend(channel, body);
        LOGGER.info("Message sent");
    }
}
