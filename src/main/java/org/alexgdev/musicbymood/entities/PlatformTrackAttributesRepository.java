package org.alexgdev.musicbymood.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformTrackAttributesRepository extends CrudRepository<PlatformTrackAttributes, Long>{
	
	PlatformTrackAttributes findOneByPlatformAndTrack(MusicPlatform platform, Track track);
	Long deleteByPlatform(MusicPlatform platform);

}
