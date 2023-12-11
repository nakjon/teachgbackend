package com.techg.spring.payload;


import java.util.Set;

import com.techg.spring.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDtoRes {   

	 private int id ;

	 private String name ;
	 
	 private String email ;
	  
}
