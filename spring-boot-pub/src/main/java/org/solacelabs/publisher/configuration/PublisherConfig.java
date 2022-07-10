package org.solacelabs.publisher.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class PublisherConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherConfig.class);

    private final ConnectionFactory connectionFactory;

    public PublisherConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    // Example configuration of the ConnectionFactory: we instantiate it here ourselves and set an error handler
    @Bean
    public DefaultJmsListenerContainerFactory cFactory(ConnectionFactory connectionFactory, DemoErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        CachingConnectionFactory ccf = new CachingConnectionFactory(connectionFactory);
        return new JmsTemplate(ccf);
    }

    @Service
    public static class DemoErrorHandler implements ErrorHandler{

        public void handleError(Throwable t) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            t.printStackTrace(ps);
            String output = os.toString(StandardCharsets.UTF_8);
            LOGGER.error("============= Error processing message: '{}'\nOutput: '{}'", t.getMessage(), output);
        }
    }


}