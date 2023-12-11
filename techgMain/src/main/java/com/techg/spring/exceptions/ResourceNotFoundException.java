package com.techg.spring.exceptions;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	
	
	 String resourceName ; String fieldName ; long fieldValue ;
	 
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		
		super(String.format("%s not found with %s : %s",resourceName ,fieldName,fieldValue));
	}
   public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
		
		super(String.format("%s not found with %s : %s",resourceName ,fieldName,fieldValue));
	}

	public ResourceNotFoundException(String resourceName ,String fieldName) {
		super(String.format("%s not found with given %s",resourceName ,fieldName));
	}
	
	
	
	
}
