package org.alexgdev.musicbymood.entities;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicPlatformRepository extends CrudRepository<MusicPlatform, Long>{
	
	MusicPlatform findOneByName(String name);

}
