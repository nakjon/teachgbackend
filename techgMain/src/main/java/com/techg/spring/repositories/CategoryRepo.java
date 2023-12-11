package com.techg.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techg.spring.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
     
}
