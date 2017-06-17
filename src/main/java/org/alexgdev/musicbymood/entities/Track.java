package org.alexgdev.musicbymood.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.alexgdev.musicbymood.dto.sc.SCTrackDTO;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name="track")
@EqualsAndHashCode(exclude={"genres", "availablePlatforms"})
@ToString(exclude={"genres", "availablePlatforms"})
public class Track {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
	
	
	@ManyToMany(mappedBy = "availableTracks", cascade = CascadeType.MERGE)
    @JsonBackReference
    private List<Genre> genres;
	
	@OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<PlatformTrackAttributes> availablePlatforms;
	
	@NotNull
	private String title;
	
	@NotNull
	private String artist;
	
	private Date releaseDate;
	
	public Track(){};
	
	public Track(SCTrackDTO scdto){
		genres = new ArrayList<Genre>();
		availablePlatforms = new ArrayList<PlatformTrackAttributes>();
		title = scdto.getTitle();
		artist = scdto.getUser().getUsername();
		releaseDate = scdto.getRelease_date();
	}

}
