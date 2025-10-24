package com.wayne.waynesecurity.services.exceptions;

public class SecurityException extends RuntimeException { 
	private static final long serialVersionUID = 1L;

	public SecurityException(String msg) {
		super(msg);
	}
}