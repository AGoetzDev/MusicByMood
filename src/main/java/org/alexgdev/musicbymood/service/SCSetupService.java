package org.alexgdev.musicbymood.service;




import org.alexgdev.musicbymood.config.SCConfig;
import org.alexgdev.musicbymood.config.SCConfig.SCGenre;
import org.alexgdev.musicbymood.entities.Genre;
import org.alexgdev.musicbymood.entities.GenreRepository;
import org.alexgdev.musicbymood.entities.MusicPlatform;
import org.alexgdev.musicbymood.entities.MusicPlatformRepository;
import org.alexgdev.musicbymood.entities.PlatformGenreAttributes;
import org.alexgdev.musicbymood.entities.PlatformGenreAttributesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SCSetupService {
	
	@Autowired
	private GenreRepository grepo;
	
	@Autowired
	private MusicPlatformRepository prepo;
	
	@Autowired
	private PlatformGenreAttributesRepository pgrepo;
	

	
	@Autowired
    private SCConfig scconfig;
	
	public void setup(){
		MusicPlatform sc = prepo.findOneByName("soundcloud");
		if(sc == null){
			sc = new MusicPlatform();
			sc.setApiKey(scconfig.getKey());
			sc.setName("soundcloud");
			prepo.save(sc);
		}
		
		for(SCGenre scgenre: scconfig.getGenres()){
			Genre genre = grepo.findOneByName(scgenre.getName());
			if(genre == null){
				genre = new Genre();
				genre.setName(scgenre.getName());
				//Set<Emotion> emotions = new HashSet<Emotion>();
				//String[] emotionsStr = scgenre.getEmotions().split(",");
				//for(String s : emotionsStr){
				//	emotions.add(Emotion.valueOf(s));
				//}
				genre.setEmotions(scgenre.getEmotions());
				grepo.save(genre);
				
				
			}
			PlatformGenreAttributes genreattrib = pgrepo.findOneByPlatformAndGenre(sc, genre);
			if(genreattrib == null){
				genreattrib = new PlatformGenreAttributes();
				genreattrib.setGenre(genre);
				genreattrib.setPlatform(sc);
				genreattrib.setIdentifier(scgenre.getIdentifier());
				
				pgrepo.save(genreattrib);
			}
		}
		
		
		
	}
	


}
