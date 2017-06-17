package org.alexgdev.musicbymood.exception;



import org.alexgdev.musicbymood.dto.MessageDTO;
import org.alexgdev.musicbymood.dto.MessageType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	
	
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO handleServiceConflict(ServiceException ex) {
	    return new MessageDTO(MessageType.ERROR, ex.getMessage(), null);
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public MessageDTO handleNotFoundConflict(NotFoundException ex) {
	    return new MessageDTO(MessageType.ERROR, ex.getMessage(), null);
	}
	



}