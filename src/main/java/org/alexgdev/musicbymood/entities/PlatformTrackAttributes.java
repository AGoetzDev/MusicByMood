package org.alexgdev.musicbymood.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.alexgdev.musicbymood.dto.sc.SCTrackDTO;

import lombok.Data;

@Entity
@Table(name="platform_track")
@Data
public class PlatformTrackAttributes {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "musicplatform_id")
	private MusicPlatform platform;
	
	
    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;
	
    @Column(columnDefinition="TEXT")
	private String artwork_url;
    
	private String embeddable_by;
	@Column(columnDefinition="TEXT")
	private String uri;
	private Long likes_count;
	private Long playback_count;
	@Column(columnDefinition="TEXT")
	private String permalink_url;
	private Long idOnPlatform;
	
	public PlatformTrackAttributes(){}
	public PlatformTrackAttributes(SCTrackDTO sctrack){
		artwork_url= sctrack.getArtwork_url();
		embeddable_by = sctrack.getEmbeddable_by();
		likes_count = sctrack.getLikes_count();
		playback_count = sctrack.getPlayback_count();
		permalink_url = sctrack.getPermalink_url();
		uri = sctrack.getUri();
		idOnPlatform = sctrack.getId();
	}

}
