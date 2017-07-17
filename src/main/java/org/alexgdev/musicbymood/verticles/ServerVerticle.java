package org.alexgdev.musicbymood.verticles;

import java.util.Map;

import org.alexgdev.musicbymood.dto.MessageDTO;
import org.alexgdev.musicbymood.dto.MessageType;
import org.alexgdev.musicbymood.exception.ServiceException;
import org.springframework.stereotype.Component;



import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.ReplyException;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.FaviconHandler;
import io.vertx.ext.web.handler.StaticHandler;

@Component
public class ServerVerticle extends AbstractVerticle {
	

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.route("/api/mood").handler(BodyHandler.create());
        router.post("/api/mood")
            .handler(this::handleGeneratePlaylist);
        router.route("/*").handler(StaticHandler.create("static").setCachingEnabled(false));
        router.route().handler(FaviconHandler.create("static/favicon.ico"));

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(config().getInteger("http.port", 8080));
    }
    
    public void handleGeneratePlaylist(RoutingContext context){
    	String dataUrl = context.request().formAttributes().get("dataUrl");
    	if(dataUrl == null || dataUrl.equals("")){
    		MessageDTO dto = new MessageDTO(MessageType.ERROR, "No Image sent!", null);
            
            context.response()
            	.putHeader("content-type", "application/json")
                .setStatusCode(400)
                .end(JsonObject.mapFrom(dto).encodePrettily());
    	} else {
    		vertx.eventBus()
            .<String>send(PlaylistVerticle.GET_PLAYLIST_SC, dataUrl, result -> {
                if (result.succeeded()) {
                    
                    context.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(200)
                        .end(result.result().body());
                } else {
                	ReplyException cause = (ReplyException) result.cause();
                    MessageDTO dto = new MessageDTO(MessageType.ERROR, cause.getMessage(), null);
                    
                    context.response()
                    	.putHeader("content-type", "application/json")
                        .setStatusCode(cause.failureCode())
                        .end(JsonObject.mapFrom(dto).encodePrettily());
                }
            });
    	}

		
	}
    
    
    

}