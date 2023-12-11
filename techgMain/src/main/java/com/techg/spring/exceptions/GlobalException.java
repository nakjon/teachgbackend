package com.techg.spring.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.techg.spring.payload.ApiResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message =ex.getMessage();
        Integer status=0;
        ApiResponse apiResponse=new ApiResponse(message, status);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(DuplicateEntry.class)
    public ResponseEntity<?> alreadyExistExceptionHandler(DuplicateEntry ex){
        String message =ex.getMessage();
        Integer status=0;
        ApiResponse apiResponse=new ApiResponse(message, status);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message) ;
		});
		return new ResponseEntity<Map<String, String>>(resp ,HttpStatus.BAD_REQUEST); 
	}

}
