package solacelabs.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import solacelabs.publisher.configuration.PublisherProperties;

@SpringBootApplication
@EnableConfigurationProperties(PublisherProperties.class)
public class PublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublisherApplication.class, args);
	}

}
