package com.example.ProjectUsingWatcher;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;



@ControllerAdvice
public class GlobalExceptionHandler {
	
	// user defined exception
    @ExceptionHandler(KeyNotFoundException.class)
    public ResponseEntity<String> handleKeyNotFoundException(KeyNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler(IOException.class)
	public ResponseEntity<Map<String, Object>> handleIOException(IOException ex) {
		
	    Map<String, Object> response = new HashMap<>();
	    //response.put("timestamp", LocalDateTime.now());
	    response.put("error", "File Access Error");
	    response.put("message", ex.getMessage());
	    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    
	}
	
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
    	
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}