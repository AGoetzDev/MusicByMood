package org.alexgdev.musicbymood.dto;

import org.alexgdev.musicbymood.entities.Track;

import lombok.Data;

@Data
public class TrackDTO {
	
	
	private String genre;
	private String artist;
	private String title;
	private String link;
	private Long idOnPlatform;
	
	public TrackDTO(){};
	
	public TrackDTO(Track track){
		this.genre = track.getGenres().get(0).getName();
		this.title = track.getTitle();
		this.artist = track.getArtist();
	}

}
