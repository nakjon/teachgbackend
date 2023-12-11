package com.techg.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techg.spring.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public Optional<User> findByEmail(String email) ;
	
	
}
