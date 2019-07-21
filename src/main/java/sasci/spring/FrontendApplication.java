package sasci.spring;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;

@SpringBootApplication
// Enable consumption of HATEOS payloads
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
// Enable Feign Clients
@EnableFeignClients
public class FrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}

	@Bean
	@ServiceActivator(inputChannel = "messagesOutputChannel")
	public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, "messages");
	}

	// This configures the Vision API settings with a
	// credential using the the scope we specified in
	// the application.properties.
	@Bean
	public ImageAnnotatorSettings imageAnnotatorSettings(CredentialsProvider credentialsProvider) throws IOException {
		return ImageAnnotatorSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
	}

	@Bean
	public ImageAnnotatorClient imageAnnotatorClient(ImageAnnotatorSettings settings) throws IOException {
		return ImageAnnotatorClient.create(settings);
	}
}
