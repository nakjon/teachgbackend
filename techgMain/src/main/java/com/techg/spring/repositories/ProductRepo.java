package com.techg.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techg.spring.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	
}
