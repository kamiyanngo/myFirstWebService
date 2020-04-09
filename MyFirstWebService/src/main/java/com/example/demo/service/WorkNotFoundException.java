package com.example.demo.service;

public class WorkNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public WorkNotFoundException(String message) {
		super(message);
	}
}
