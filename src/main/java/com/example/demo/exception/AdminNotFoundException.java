package com.example.demo.exception;

public class AdminNotFoundException extends RuntimeException{
public AdminNotFoundException(String message) {
	super(message);
}
}
