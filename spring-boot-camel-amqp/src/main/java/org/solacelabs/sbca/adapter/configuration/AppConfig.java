package org.solacelabs.sbca.adapter.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        SbCamelSolaceAdapterProperties.class
})
public class AppConfig {
}
