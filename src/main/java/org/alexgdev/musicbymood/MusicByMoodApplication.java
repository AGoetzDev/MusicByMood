package org.alexgdev.musicbymood;


import org.alexgdev.musicbymood.service.SCSetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class MusicByMoodApplication implements CommandLineRunner{
	
	
	
	@Autowired SCSetupService scservice;
	
	private static final Logger log = LoggerFactory.getLogger(MusicByMoodApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MusicByMoodApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Override
	public void run(String... args) throws Exception {
		scservice.setup();
		
	}
	
	

	
}
