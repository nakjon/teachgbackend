package com.techg.spring.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int totalPrice ;
	
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user ;
	
//	  @ManyToMany(cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
//	  @JoinTable(name = "cart_products" ,
//	  joinColumns = @JoinColumn( name="cart",referencedColumnName = "id"),
//	  inverseJoinColumns = @JoinColumn( name="product",referencedColumnName = "id"))
//	  private Set<Product> products = new HashSet<>(); 
	
	
}
