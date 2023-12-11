package com.techg.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techg.spring.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByUserId(Integer userId);           
	
}
