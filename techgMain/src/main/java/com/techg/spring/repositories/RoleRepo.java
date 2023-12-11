package com.techg.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techg.spring.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

	public Optional<Role> findByName(String name) ;
}
