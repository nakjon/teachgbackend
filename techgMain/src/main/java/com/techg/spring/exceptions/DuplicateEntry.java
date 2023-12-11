package com.techg.spring.exceptions;

public class DuplicateEntry extends RuntimeException{
	
public DuplicateEntry(String resourceName, String fieldName, String fieldValue) {
		
		super(String.format("%s with %s : %s is already present",resourceName ,fieldName,fieldValue));

		/*
		 * this.resourceName = resourceName; this.fieldName = fieldName; this.fieldValue
		 * = fieldValue;
		 */
	}

}
