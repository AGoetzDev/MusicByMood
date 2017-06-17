package org.alexgdev.musicbymood.dto.sc;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SCChartDTO {
	private String genre;
	private String kind;
	private String last_updated;
	private List<SCChartContentDTO> collection;
	private String queryurn;
	private String next_href;
	

}
