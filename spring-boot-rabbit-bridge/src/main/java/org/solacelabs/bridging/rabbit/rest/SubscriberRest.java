package org.solacelabs.bridging.rabbit.rest;

import org.solacelabs.bridging.rabbit.service.SubscriberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solace/spring-rabbit-bridge/subscriber")
public class SubscriberRest {

    private final SubscriberService subscriberService;

    public SubscriberRest(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("topic")
    public ResponseEntity<String> subscribeToTopic(
            @RequestParam("name") String topicName) {
        subscriberService.registerTopicListener(topicName);
        return ResponseEntity.status(HttpStatus.OK).body("Subscriber created");
    }

    @PostMapping("queue")
    public ResponseEntity<String> subscribeToQueue(
            @RequestParam("name") String queueName) {
        subscriberService.registerQueueListener(queueName);
        return ResponseEntity.status(HttpStatus.OK).body("Subscriber created");
    }

}
