package org.solacelabs.java.client.plain.producer;

import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishCorrelatingEventHandler;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.XMLMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicPublisher.class);

    public static void main(String... args) throws JCSMPException {

        // Check command line arguments
        if (args.length != 4 || args[1].split("@").length != 2) {
            LOGGER.info("Usage: TopicPublisher <host:port> <client-username@message-vpn> <client-password> <topicName>");
            System.exit(-1);
        }
        if (args[1].split("@")[0].isEmpty()) {
            LOGGER.info("No client-username entered");
            System.exit(-1);
        }
        if (args[1].split("@")[1].isEmpty()) {
            LOGGER.info("No message-vpn entered");
            System.exit(-1);
        }

        LOGGER.info("TopicPublisher initializing...");

        // Create a JCSMP Session
        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, args[0]);     // host:port
        properties.setProperty(JCSMPProperties.USERNAME, args[1].split("@")[0]); // client-username
        properties.setProperty(JCSMPProperties.VPN_NAME, args[1].split("@")[1]); // message-vpn
        properties.setProperty(JCSMPProperties.PASSWORD, args[2]); // client-password

        final String topicName = args[3];
        final JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);

        session.connect();

        final Topic topic = JCSMPFactory.onlyInstance().createTopic(topicName);

        // Anonymous inner-class for handling publishing events
        XMLMessageProducer producer = session.getMessageProducer(new JCSMPStreamingPublishCorrelatingEventHandler() {

            @Override
            public void responseReceivedEx(Object o) {
                LOGGER.info("Producer received response");
            }

            @Override
            public void handleErrorEx(Object o, JCSMPException e, long l) {
                LOGGER.error("handleErrorEx: an error has occured ");
            }

            @Override
            public void handleError(String messageID, JCSMPException cause, long timestamp) {
                JCSMPStreamingPublishCorrelatingEventHandler.super.handleError(messageID, cause, timestamp);
                LOGGER.error("Producer received error for msg: {}@{}", messageID, timestamp, cause);
            }

            @Override
            public void responseReceived(String messageID) {
                JCSMPStreamingPublishCorrelatingEventHandler.super.responseReceived(messageID);
                LOGGER.info("Producer received response for msg: {}", messageID);
            }
        });

        // Publish-only session is now hooked up and running!
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        final String text = "Hello world!";
        msg.setText(text);
        LOGGER.info("Connected. About to send message '{}' to topic '{}'...", text, topic.getName());
        producer.send(msg, topic);
        LOGGER.info("Message sent. Exiting.");
        session.closeSession();
    }
}
