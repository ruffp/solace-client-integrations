package org.solacelabs.bridging.rabbit.rest;

import org.solacelabs.bridging.rabbit.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solace/spring-rabbit-bridge/publisher")
public class PublisherRest {

    private final PublisherService publisherService;

    public PublisherRest(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("topic")
    public ResponseEntity<String> postMessageInTopic(
            @RequestParam("name") String topicName,
            @RequestBody String body) {
        publisherService.publishMessage(topicName, body, true);
        return ResponseEntity.status(HttpStatus.OK).body("Message Sent");
    }

    @PostMapping("queue")
    public ResponseEntity<String> postMessageInQueue(
            @RequestParam("name") String queueName,
            @RequestBody String body) {
        publisherService.publishMessage(queueName, body, false);
        return ResponseEntity.status(HttpStatus.OK).body("Message Sent");
    }

}
