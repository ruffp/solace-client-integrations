package org.solacelabs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="subscriber")
public final record SubscriberProperties(Boolean enabled) {

}
