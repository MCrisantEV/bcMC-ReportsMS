package mc.bc.ms.reports.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Value("${personPort:8004}")
	private String personPort;

	@Value("${inscriptionPort:8003}")
	private String inscriptionPort;
	
	@Value("${coursePort:8002}")
	private String coursePort;
	
	@Value("${familyPort:8006}")
	private String familyPort;
	
	@Value("${evaluationPort:8007}")
	private String evaluationPort;

	@Bean
	@Qualifier("person")
	public WebClient wcPerson() {
		return WebClient.create("http://localhost:" + personPort + "/persons");
	}
	
	@Bean
	@Qualifier("inscription")
	public WebClient wcInscription() {
		return WebClient.create("http://localhost:" + inscriptionPort + "/inscriptions");
	}
	
	@Bean
	@Qualifier("course")
	public WebClient wcCourse() {
		return WebClient.create("http://localhost:" + coursePort + "/courses");
	}
	
	@Bean
	@Qualifier("family")
	public WebClient wcFamily() {
		return WebClient.create("http://localhost:" + familyPort + "/personfamily");
	}
	
	@Bean
	@Qualifier("evaluation")
	public WebClient wcEvaluation() {
		return WebClient.create("http://localhost:" + evaluationPort + "/evaluations");
	}
	
}
