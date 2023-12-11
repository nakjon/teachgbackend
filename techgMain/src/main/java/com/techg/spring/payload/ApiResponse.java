package com.techg.spring.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

	private String message ;
	private int status ;
	private Boolean success ;
	public ApiResponse(String message, Boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
	public ApiResponse(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}
	
}
