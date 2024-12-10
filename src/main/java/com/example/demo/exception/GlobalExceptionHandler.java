package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	  @ExceptionHandler(AdminNotFoundException.class)
	    public ResponseEntity<String> handleAdminNotFoundException(AdminNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);  
	    }
	  @ExceptionHandler(InvalidPasswordException.class)
	  public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex){
		  return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	  }
	  @ExceptionHandler(QuestionNotFoundException.class)
	  public ResponseEntity<String> handleQuestionNotFoundException(QuestionNotFoundException ex){
		  return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	  }
	  @ExceptionHandler(UserNotFoundException.class)
	  public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex){
		  return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	  }
	  @ExceptionHandler(InvalidOptionException.class)
	  public ResponseEntity<String> handleInvalidOptionException(InvalidOptionException ex){
		  return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	  }
	  @ExceptionHandler(NotAllQuestionAnsweredException.class)
	  public ResponseEntity<String> handleNotAllQuestionAnsweredException(NotAllQuestionAnsweredException ex){
		  return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	  }
	  @ExceptionHandler(InvalidcredentialsException.class)
	  public ResponseEntity<String> handleInvalidcrentialsException(InvalidcredentialsException ex){
		  return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
	  }
	  
	  
}
