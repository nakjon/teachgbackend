package com.techg.spring.payload;

import java.util.Date;
import java.util.List;

import com.techg.spring.model.Product;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor 
@AllArgsConstructor
public class CertificateDto {
    
	private int id;
	
    private String name ;
	
    @Nonnull
	private String coursename;

	private String email ;

	private Long certificateno ;

	private Long mobile ;

	private Long uniRollNo ;
	
	private Date fromdate;
	
	private Date todate ;

	@Override
	public String toString() {
		return "CertificateDto [name=" + name + ", coursename=" + coursename + ", fromdate=" + fromdate + ", todate="
				+ todate + "]";
	}
	
	
}
