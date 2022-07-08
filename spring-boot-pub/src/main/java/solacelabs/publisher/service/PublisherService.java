package solacelabs.publisher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import solacelabs.publisher.configuration.PublisherProperties;

@Service
public class PublisherService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

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

    public void run(String... strings)  {
        String msg = "Hello World";
        logger.info("============= Sending: '{}'", msg);
        this.jmsTemplate.setPubSubDomain(true);
        this.jmsTemplate.convertAndSend(publisherProperties.topicName(), msg);
    }
}


