package com.example.demo.exception;

public class QuestionNotFoundException extends RuntimeException{
public QuestionNotFoundException(String message) {
	super(message);
}
}
