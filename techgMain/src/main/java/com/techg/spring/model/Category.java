package com.techg.spring.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor 
public class Category {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(name = "title" ,length = 100 ,nullable = false)
	private String categoryTitle;
	
	@Column(name = "discription")
	private String categoryDiscription;
	
	@OneToMany(mappedBy = "category" ,fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>(); 
	
}
