package org.solacelabs.subscriber;

import java.util.Iterator;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberService.class);

    // Retrieve the name of the queue from the application.properties file
    @JmsListener(destination = "${subscriber.queueName}", containerFactory = "cFactory", concurrency = "2")
    public void processMsg(Message<?> msg) {
        StringBuilder msgAsStr = new StringBuilder("============= Received \nHeaders:");
        MessageHeaders hdrs = msg.getHeaders();
        msgAsStr.append("\nUUID: " + hdrs.getId());
        msgAsStr.append("\nTimestamp: " + hdrs.getTimestamp());
        Iterator<String> keyIter = hdrs.keySet().iterator();
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            msgAsStr.append("\n" + key + ": " + hdrs.get(key));
        }
        msgAsStr.append("\nPayload: " + msg.getPayload());
        logger.info(msgAsStr.toString());
    }

}
