package org.alexgdev.musicbymood.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformGenreAttributesRepository extends CrudRepository<PlatformGenreAttributes, Long>{
	
	PlatformGenreAttributes findOneByPlatformAndGenre(MusicPlatform platform, Genre genre);

}
