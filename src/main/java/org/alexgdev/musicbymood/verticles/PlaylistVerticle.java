package org.alexgdev.musicbymood.verticles;



import org.alexgdev.musicbymood.dto.MessageDTO;
import org.alexgdev.musicbymood.dto.MessageType;
import org.alexgdev.musicbymood.dto.TrackListDTO;
import org.alexgdev.musicbymood.exception.NotFoundException;
import org.alexgdev.musicbymood.exception.ServiceException;
import org.alexgdev.musicbymood.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@Component
public class PlaylistVerticle extends AbstractVerticle{
	public static final String GET_PLAYLIST_SC = "get.playlist.sc";

    private final ObjectMapper mapper = Json.mapper;
    private static final Logger log = LoggerFactory.getLogger(PlaylistVerticle.class);

    @Autowired
	private TrackService trackService;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus()
                .<String>consumer(GET_PLAYLIST_SC)
                .handler(getSoundCloudPlaylist(trackService));  
    }

    private Handler<Message<String>> getSoundCloudPlaylist(TrackService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
            	TrackListDTO dto = service.getSoundCloudTracklistFromImage(msg.body());
            	MessageDTO response = new MessageDTO(MessageType.SUCCESS, "success", dto);
            	future.complete(mapper.writeValueAsString(response));
            } catch (JsonProcessingException | ServiceException e) {
                log.error("Failed to serialize result");
                future.fail(e);
            }
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
            	if(result.cause() instanceof NotFoundException){
            		msg.fail(404, result.cause().getMessage());
            	} else if(result.cause() instanceof ServiceException){
            		msg.fail(400, result.cause().getMessage());
            	} else {
            		msg.fail(500, result.cause().getMessage());
            	}
            }
        });
    }
    
}
