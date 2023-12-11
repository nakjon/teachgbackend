package com.techg.spring.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotEmpty
	 @Size(min = 3 ,message = "title must be grater than 2")
	private String categoryTitle;
	
	@Size(min = 10 ,message = "Discription must be grater than 9 char")
	private String categoryDiscription;
	
}
