package org.solacelabs.sbca.adapter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@ConfigurationProperties(prefix = "main")
@Validated
public record SbCamelSolaceAdapterProperties(String env) {
}
