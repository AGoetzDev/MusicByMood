package org.alexgdev.musicbymood.dto;

import java.util.List;

import org.alexgdev.musicbymood.entities.Emotion;

import lombok.Data;

@Data
public class TrackListDTO {
	private Emotion emotion;
	private List<TrackDTO> tracks;
	
}
