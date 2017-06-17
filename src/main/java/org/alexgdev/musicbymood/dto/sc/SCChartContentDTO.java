package org.alexgdev.musicbymood.dto.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SCChartContentDTO {
	
	private SCTrackDTO track;
	private Double score;

}
