package org.solacelabs.configuration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@EnableJms
@Configuration
public class SubscriberConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberConfig.class);

    // Example configuration of the ConnectionFactory: we instantiate it here ourselves and set an error handler
    @Bean
    public DefaultJmsListenerContainerFactory cFactory(ConnectionFactory connectionFactory, DemoErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Service
    public class DemoErrorHandler implements ErrorHandler{   

        public void handleError(Throwable t) {
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	PrintStream ps = new PrintStream(os);
        	t.printStackTrace(ps);
        	try {
				String output = os.toString("UTF8");
	            LOGGER.error("============= Error processing message: '{}'\nOutput: '{}'", t.getMessage(), output);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
        }
    }

}