package org.solacelabs.java.client.plain.subscriber;

import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.ConsumerFlowProperties;
import com.solacesystems.jcsmp.EndpointProperties;
import com.solacesystems.jcsmp.FlowReceiver;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.Queue;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.XMLMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class QueueSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueSubscriber.class);
    private static final int MSG_COUNT = 100;

    public static void main(String... args) throws Exception {
        // Check command line arguments
        if (args.length < 4 || args[1].split("@").length != 2) {
            LOGGER.info("Usage: QueueSubscriber <host:port> <client-username@message-vpn> <client-password> <queueName>");
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

        LOGGER.info("QueueSubscriber initializing...");

        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, args[0]);
        properties.setProperty(JCSMPProperties.USERNAME, args[1].split("@")[0]);
        properties.setProperty(JCSMPProperties.VPN_NAME, args[1].split("@")[1]);
        properties.setProperty(JCSMPProperties.PASSWORD, args[2]);
        final String queueName = args[3];

        final JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);

        session.connect();
        LOGGER.info("Successfully Connected!");

        // The subscriber consumes MSG_COUNT messages then exit
        final CountDownLatch latch = new CountDownLatch(MSG_COUNT);

        LOGGER.info("Attempting to provision the queue '{}' on the appliance.", queueName);
        final EndpointProperties endpointProps = new EndpointProperties();
        // set queue permissions to "consume" and access-type to "exclusive"
        endpointProps.setPermission(EndpointProperties.PERMISSION_CONSUME);
        endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);
        // create the queue object locally
        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);

        final ConsumerFlowProperties flowProp = new ConsumerFlowProperties();
        flowProp.setEndpoint(queue);
        flowProp.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_CLIENT);

        final FlowReceiver cons = session.createFlow(new XMLMessageListener() {
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    LOGGER.info("TextMessage received: '{}'", ((TextMessage) msg).getText());
                } else {
                    LOGGER.info("Message received.");
                }
                LOGGER.info("Message Dump:%n{}}%n", msg.dump());
                // When the ack mode is set to SUPPORTED_MESSAGE_ACK_CLIENT,
                // guaranteed delivery messages are acknowledged after
                // processing
                msg.ackMessage();
                latch.countDown(); // unblock main thread
            }

            @Override
            public void onException(JCSMPException e) {
                LOGGER.error("Consumer received exception:", e);
                latch.countDown(); // unblock main thread
            }
        }, flowProp, endpointProps);


        cons.start();

        try {
            latch.await(); // block here until message received, and latch will flip
            LOGGER.info("Received '{}' message, then exiting", MSG_COUNT);
        } catch (InterruptedException e) {
            LOGGER.info("I was awoken while waiting");
        }


        LOGGER.info("Exiting.");
        session.closeSession();
    }
}
