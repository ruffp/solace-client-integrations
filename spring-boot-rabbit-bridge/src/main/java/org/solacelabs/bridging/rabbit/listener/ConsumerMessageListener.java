package org.solacelabs.bridging.rabbit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerMessageListener.class);

    private final String consumerName;

    public ConsumerMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage textMessage) {
            try {
                LOGGER.info("Consumer:'{}' received message: '{}'", consumerName, textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
