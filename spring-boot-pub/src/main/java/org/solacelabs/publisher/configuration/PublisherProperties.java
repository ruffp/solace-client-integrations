package org.solacelabs.publisher.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="publisher")
public record PublisherProperties(Boolean enabled, String topicName) {

}
