package com.infa.idt.tools.build.exception;

public class InvalidArgumentException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	
	public InvalidArgumentException(String error){
		this.errorMessage = error;
	}
	
	public String getMessage() {
		return this.errorMessage;
	}
}
