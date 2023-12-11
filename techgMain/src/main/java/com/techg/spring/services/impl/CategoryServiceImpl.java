package com.techg.spring.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techg.spring.exceptions.ResourceNotFoundException;
import com.techg.spring.model.Category;
import com.techg.spring.payload.CategoryDto;
import com.techg.spring.repositories.CategoryRepo;
import com.techg.spring.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper ;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = modelMapper.map(categoryDto, Category.class);
	    Category addCat = categoryRepo.save(cat);
		return modelMapper.map(addCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		 
		Category cat = categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDiscription(categoryDto.getCategoryDiscription());
		
		Category category2 = categoryRepo.save(cat);
		
		return modelMapper.map(category2, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
		categoryRepo.delete(cat); 
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
	List<Category> categories=	categoryRepo.findAll();
	List<CategoryDto> catDtos = categories.stream().map((cat)->modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());	
	return catDtos;
	}

}
