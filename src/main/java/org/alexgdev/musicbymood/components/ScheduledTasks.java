package org.alexgdev.musicbymood.components;

import java.util.ArrayList;
import java.util.List;

import org.alexgdev.musicbymood.dto.sc.SCChartContentDTO;
import org.alexgdev.musicbymood.dto.sc.SCChartDTO;
import org.alexgdev.musicbymood.entities.Genre;
import org.alexgdev.musicbymood.entities.GenreRepository;
import org.alexgdev.musicbymood.entities.MusicPlatform;
import org.alexgdev.musicbymood.entities.MusicPlatformRepository;
import org.alexgdev.musicbymood.entities.PlatformGenreAttributes;
import org.alexgdev.musicbymood.entities.PlatformTrackAttributes;
import org.alexgdev.musicbymood.entities.PlatformTrackAttributesRepository;
import org.alexgdev.musicbymood.entities.Track;
import org.alexgdev.musicbymood.entities.TrackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	@Autowired
	private GenreRepository grepo;
	
	@Autowired
	private MusicPlatformRepository prepo;
	
	@Autowired
	private PlatformTrackAttributesRepository ptrepo;
	
	@Autowired
	private TrackRepository trepo;
	
	@Scheduled(fixedDelay = 3600000)
	@Transactional 
	public void getSCTop50(){
		
		log.info("Getting SoundCloud Top 50");
		MusicPlatform sc = prepo.findOneByName("soundcloud");
		if(sc != null){
			String url = "https://api-v2.soundcloud.com/charts?kind=top&high_tier_only=false&limit=50&offset=0&linked_partitioning=1" +"&client_id="+sc.getApiKey();
			ptrepo.deleteByPlatform(sc);
			RestTemplate restTemplate = new RestTemplate();
			for(PlatformGenreAttributes scgenre: sc.getAvailableGenres()){
				
				SCChartDTO chart = restTemplate.getForObject(url+"&genre="+scgenre.getIdentifier(), SCChartDTO.class);
				Genre genre = scgenre.getGenre();
				List<Track> availableTracks = new ArrayList<Track>();
				for(SCChartContentDTO track : chart.getCollection()){
					Track t = trepo.findOneByTitleAndArtist(track.getTrack().getTitle(), track.getTrack().getUser().getUsername());
					if(t == null){
						t = new Track(track.getTrack());
						trepo.save(t);
					}
					availableTracks.add(t);
					
					PlatformTrackAttributes attrib = new PlatformTrackAttributes(track.getTrack());	
					attrib.setPlatform(sc);
					attrib.setTrack(t);
					
					ptrepo.save(attrib);
				}
				genre.setAvailableTracks(availableTracks);
				grepo.save(genre);

				log.info("Finished Genre: "+genre.getName());
			}
			Long deletecount = trepo.deleteByAvailablePlatformsIsNull();
			log.info("Deleted "+deletecount+" tracks that were no longer in top 50");
		}
		
	}

}
