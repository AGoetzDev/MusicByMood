package org.alexgdev.musicbymood.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long>{
	
	Track findOneByTitleAndArtist(String title, String artist);
	Long deleteByAvailablePlatformsIsNull();
	List<Track> findAllByGenresEmotions(Emotion emotion);

}
