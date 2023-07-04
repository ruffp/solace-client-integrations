package org.solacelabs.publisher.configuration;

import jakarta.jms.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class PublisherConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherConfig.class);

    public PublisherConfig() {    }

}