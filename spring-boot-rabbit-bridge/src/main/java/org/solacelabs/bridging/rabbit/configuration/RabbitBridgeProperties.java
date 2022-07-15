package org.solacelabs.bridging.rabbit.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="rabbit.bridge")
public record RabbitBridgeProperties(Boolean enabled) {

}
