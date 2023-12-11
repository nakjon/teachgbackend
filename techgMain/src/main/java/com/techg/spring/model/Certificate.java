package com.techg.spring.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "certificate")
@Setter
@Getter
@NoArgsConstructor 
@AllArgsConstructor
public class Certificate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name ;
	
	@Column(unique = true)
	private String coursename;

	private String email ;
	
	private Long certificateno ;

	private Long mobile ;

	private Long uniRollNo ;
	
	private Date fromdate;
	
	private Date todate ;
}
