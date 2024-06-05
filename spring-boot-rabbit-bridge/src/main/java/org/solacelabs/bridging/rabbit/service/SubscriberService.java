package org.solacelabs.bridging.rabbit.service;

import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.Queue;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jms.impl.SolQueueImpl;
import com.solacesystems.jms.impl.SolTopicImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solacelabs.bridging.rabbit.listener.ConsumerMessageListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;

@Service
public class SubscriberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberService.class);

    private final ConnectionFactory connectionFactory;


    public SubscriberService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void registerQueueListener(String queueName) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // false for a queue, true for a topic
        container.setPubSubDomain(false);
        Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        container.setDestination(new SolQueueImpl(queue));
        container.setMessageListener(new ConsumerMessageListener("Consumer"));
        container.afterPropertiesSet();
        container.start();
        LOGGER.info("Subscriber created for queue:'{}'", queueName);
    }

    public void registerTopicListener(String topicName) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // false for a queue, true for a topic
        container.setPubSubDomain(true);
        Topic topic = JCSMPFactory.onlyInstance().createTopic(topicName);
        container.setDestination(new SolTopicImpl(topic));
        container.setMessageListener(new ConsumerMessageListener("Consumer"));
        container.afterPropertiesSet();
        container.start();
        LOGGER.info("Subscriber created for topic:'{}'", topicName);
    }
}
