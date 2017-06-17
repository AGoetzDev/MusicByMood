package org.alexgdev.musicbymood.config;

import java.util.List;

import org.alexgdev.musicbymood.entities.Emotion;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "soundcloud")
@Configuration
@Data
public class SCConfig {
	
	private String key;
	private List<SCGenre> genres;
	

	@Data
	public static class SCGenre {
		private String name;
		private String identifier;
		private List<Emotion> emotions;
		
		
	}

}
