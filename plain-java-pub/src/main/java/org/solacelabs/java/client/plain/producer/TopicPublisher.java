package org.solacelabs.java.client.plain.producer;

import com.solacesystems.jcsmp.DeliveryMode;
import com.solacesystems.jcsmp.EndpointProperties;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishEventHandler;
import com.solacesystems.jcsmp.Queue;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.XMLMessageProducer;

import java.text.DateFormat;
import java.util.Date;

public class TopicPublisher {

    public static void main(String... args) throws Exception {

        // Check command line arguments
        if (args.length < 2 || args[1].split("@").length != 2) {
            System.out.println("Usage: org.solacelabs.java.client.plain.producer.TopicPublisher <host:port> <client-username@message-vpn> [client-password]");
            System.out.println();
            System.exit(-1);
        }
        if (args[1].split("@")[0].isEmpty()) {
            System.out.println("No client-username entered");
            System.out.println();
            System.exit(-1);
        }
        if (args[1].split("@")[1].isEmpty()) {
            System.out.println("No message-vpn entered");
            System.out.println();
            System.exit(-1);
        }

        System.out.println("org.solacelabs.java.client.plain.producer.TopicPublisher initializing...");

        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, args[0]);
        properties.setProperty(JCSMPProperties.USERNAME, args[1].split("@")[0]);
        properties.setProperty(JCSMPProperties.VPN_NAME, args[1].split("@")[1]);
        if (args.length > 2) {
            properties.setProperty(JCSMPProperties.PASSWORD, args[2]);
        }

        final JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);

        session.connect();
        System.out.println("Successfully Connected!");

        // create the queue object locally
        String queueName = "Q/tutorial";
        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);

// set queue permissions to "consume" and access-type to "exclusive"
        final EndpointProperties endpointProps = new EndpointProperties();
        endpointProps.setPermission(EndpointProperties.PERMISSION_CONSUME);
        endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);

// Actually provision it, and do not fail if it already exists
        session.provision(queue, endpointProps, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);

        XMLMessageProducer prod = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {

            @Override
            public void responseReceived(String messageID) {
                System.out.println("Producer received response for msg: " + messageID);
            }

            @Override
            public void handleError(String messageID, JCSMPException e, long timestamp) {
                System.out.printf("Producer received error for msg: %s@%s - %s%n", messageID, timestamp, e);
            }
        });

        final Queue queue1 = JCSMPFactory.onlyInstance().createQueue("Q/tutorial");
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        msg.setDeliveryMode(DeliveryMode.PERSISTENT);
        String text = "Persistent Queue Tutorial! " +
                DateFormat.getDateTimeInstance().format(new Date());
        msg.setText(text);
// Delivery not yet confirmed. See ConfirmedPublish.java
        prod.send(msg, queue);

        System.out.println("Exiting.");
        session.closeSession();
    }

}
