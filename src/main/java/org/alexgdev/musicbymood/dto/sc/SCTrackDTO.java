package org.alexgdev.musicbymood.dto.sc;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SCTrackDTO {
	private String artwork_url;
	private Long id;
	private String embeddable_by;
	private String genre;
	private String title;
	private Date release_date;
	private String uri;
	private SCUserDTO user;
	private Long likes_count;
	private Long playback_count;
	private String permalink_url;
	

}
