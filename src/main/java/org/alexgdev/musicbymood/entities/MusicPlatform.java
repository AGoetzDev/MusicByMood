package org.alexgdev.musicbymood.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="platform")
@Data
@EqualsAndHashCode(exclude={"availableGenres", "availableTracks"})
@ToString(exclude={"availableGenres", "availableTracks"})
public class MusicPlatform {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    @NotNull
    @Column(unique = true)
	private String name;
    
    @NotNull
	private String apiKey;

    
    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<PlatformGenreAttributes> availableGenres;
    
    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<PlatformTrackAttributes> availableTracks;
	

}
