package org.alexgdev.musicbymood.dto.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SCUserDTO {
	private Long id;
	private String username;

}
