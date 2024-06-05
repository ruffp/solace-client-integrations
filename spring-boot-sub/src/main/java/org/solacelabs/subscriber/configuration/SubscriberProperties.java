package org.solacelabs.subscriber.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="subscriber")
public record SubscriberProperties(Boolean logEnabled) {

}
