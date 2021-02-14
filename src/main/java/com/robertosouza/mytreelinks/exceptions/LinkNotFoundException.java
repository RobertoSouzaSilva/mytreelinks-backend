package com.robertosouza.mytreelinks.exceptions;

public class LinkNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public LinkNotFoundException(String msg) {
		super(msg);
	}

}
