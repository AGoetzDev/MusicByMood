package org.alexgdev.musicbymood.controller;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import org.alexgdev.musicbymood.dto.MessageDTO;
import org.alexgdev.musicbymood.dto.MessageType;
import org.alexgdev.musicbymood.exception.ServiceException;
import org.alexgdev.musicbymood.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/mood")
public class MainController {
	
	
	@Autowired
	private TrackService trackService;
	
	@RequestMapping(method = RequestMethod.POST, value = "", produces = APPLICATION_JSON_VALUE)
	public MessageDTO classifyImage(@RequestParam(value = "dataUrl", required = true) String dataUrl)throws ServiceException{
		
		
		
		return new MessageDTO(MessageType.SUCCESS, "Success", trackService.getSoundCloudTracklistFromImage(dataUrl));
		
		
		
	}

}
