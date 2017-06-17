package org.alexgdev.musicbymood.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.alexgdev.musicbymood.dto.TrackDTO;
import org.alexgdev.musicbymood.dto.TrackListDTO;
import org.alexgdev.musicbymood.entities.Emotion;
import org.alexgdev.musicbymood.entities.MusicPlatform;
import org.alexgdev.musicbymood.entities.MusicPlatformRepository;
import org.alexgdev.musicbymood.entities.PlatformTrackAttributes;
import org.alexgdev.musicbymood.entities.PlatformTrackAttributesRepository;
import org.alexgdev.musicbymood.entities.Track;
import org.alexgdev.musicbymood.entities.TrackRepository;
import org.alexgdev.musicbymood.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
	
	@Autowired
	private FERService ferService;
	
	@Autowired
	private TrackRepository trackRepo;
	
	@Autowired
	private MusicPlatformRepository platformRepo;
	
	@Autowired
	private PlatformTrackAttributesRepository attribRepo;
	
	private static final Logger log = LoggerFactory.getLogger(TrackService.class);
	
	
	public TrackListDTO getSoundCloudTracklistFromImage(String dataUrl) throws ServiceException{
		MusicPlatform sc = platformRepo.findOneByName("soundcloud");
		Emotion e = ferService.classifyBase64Image(dataUrl);
		TrackListDTO result = new TrackListDTO();
		result.setEmotion(e);
		
		List<Track> tracklist = trackRepo.findAllByGenresEmotions(e);
		Collections.shuffle(tracklist);
		tracklist = tracklist.subList(0, 50);
		
		List<TrackDTO> resultList = new ArrayList<TrackDTO>();
		TrackDTO tdto;
		PlatformTrackAttributes attribs;
		for(Track t: tracklist){
			tdto = new TrackDTO(t);
			attribs = attribRepo.findOneByPlatformAndTrack(sc, t);
			if(attribs != null){
				tdto.setLink(attribs.getPermalink_url());
				tdto.setIdOnPlatform(attribs.getIdOnPlatform());
			}
			resultList.add(tdto);
		}
		 result.setTracks(resultList);
		return result;
		
		
	}

}
