package com.techg.spring.payload;



import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
	
	private int id;
	
	private String name;
	
	private String description ;
	
	private CategoryDto category ;
	
	private String imageName;
	
	private int price ; 
	
	private int qty ;
}
