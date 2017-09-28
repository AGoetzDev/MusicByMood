package org.alexgdev.musicbymood;


import org.alexgdev.musicbymood.service.SCSetupService;
import org.alexgdev.musicbymood.verticles.PlaylistVerticle;
import org.alexgdev.musicbymood.verticles.ServerVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import io.vertx.core.Vertx;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class MusicByMoodApplication implements CommandLineRunner{
	
	
	
	@Autowired SCSetupService scservice;
	@Autowired PlaylistVerticle playlistVerticle;
	@Autowired ServerVerticle serverVerticle;
	
	private static final Logger log = LoggerFactory.getLogger(MusicByMoodApplication.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplicationBuilder()
             .sources(MusicByMoodApplication.class)
             .web(false)
             .build();
			springApplication.run(args); 
		

	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
    TaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
			scservice.setup();
			final Vertx vertx = Vertx.vertx();
	        vertx.deployVerticle(serverVerticle);
	        vertx.deployVerticle(playlistVerticle);

		
		
	}
	
	

	
}
