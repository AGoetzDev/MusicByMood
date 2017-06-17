package org.alexgdev.musicbymood.exception;

public class NotFoundException extends Exception{

	private static final long serialVersionUID = 4L;
	public NotFoundException() { super(); }
	public NotFoundException(String message) { super(message); }
	public NotFoundException(String message, Throwable cause) { super(message, cause); }
	public NotFoundException(Throwable cause) { super(cause); }
}