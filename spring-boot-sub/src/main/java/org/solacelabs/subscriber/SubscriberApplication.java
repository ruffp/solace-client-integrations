package org.solacelabs.subscriber;

import org.solacelabs.subscriber.configuration.SubscriberProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SubscriberProperties.class)
public class SubscriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriberApplication.class, args);
	}

}
