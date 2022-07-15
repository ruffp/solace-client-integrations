package org.solacelabs.bridging.rabbit;

import org.solacelabs.bridging.rabbit.configuration.RabbitBridgeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RabbitBridgeProperties.class)
public class RabbitBridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitBridgeApplication.class, args);
	}

}
