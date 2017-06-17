package org.alexgdev.musicbymood.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Table(name="genre")
@Data
@EqualsAndHashCode(exclude={"availablePlatforms", "availableTracks"})
@ToString(exclude={"availablePlatforms", "availableTracks"})
public class Genre {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
	
	@OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<PlatformGenreAttributes> availablePlatforms;
	
	@ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "genre_track", joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id"))
	private List<Track> availableTracks;	
    	
	
	@NotNull
	@Column(unique = true)
	private String name;
	
	@NotNull
	@ElementCollection(targetClass=Emotion.class)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="genre_emotion")
    @Column(name="interest") // Column name in person_interest
	private List<Emotion> emotions;
	
    

}
