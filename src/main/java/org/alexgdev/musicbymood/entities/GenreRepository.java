package org.alexgdev.musicbymood.entities;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long>{
	Genre findOneByName(String name);
	List<Genre> findAllByAvailablePlatformsPlatform (MusicPlatform platform);
}
