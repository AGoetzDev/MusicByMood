package org.alexgdev.musicbymood.entities;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="platform_genre")
@Data
public class PlatformGenreAttributes {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "musicplatform_id")
	private MusicPlatform platform;
	
	
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
	
	
    private String identifier;

}
