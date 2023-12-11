package com.techg.spring.payload;


import java.util.Set;

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
public class UserDto {   

	 private int id ;

	 @NotEmpty
	 @Size(min = 4 ,message = "name must be grater than 3")
	 private String name ;
	 
	 @Email(message = "enter a valid email")
	 private String email ;
	 @NotEmpty
	 @Size(min = 3 ,max = 10 ,message = "password must be between 3 to 10 char")
	 private String password ;
	 
	 //@NotEmpty
	 
		/* private Set<String> roles; */
	  
}
