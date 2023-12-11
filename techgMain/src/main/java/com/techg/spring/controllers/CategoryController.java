package com.techg.spring.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techg.spring.payload.ApiResponse;
import com.techg.spring.payload.CategoryDto;
import com.techg.spring.payload.UserDto;
import com.techg.spring.services.CategoryService;

import jakarta.validation.Valid;

@RequestMapping("techg/api/")
@RestController
public class CategoryController {

	@Autowired       
	private CategoryService  categoryService ;
	
	@PostMapping("admin/createCategory") 
	public ResponseEntity<CategoryDto> cretaeCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory ,HttpStatus.CREATED);
		
	}
	
	@PostMapping("admin/updateCategory")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto ,@RequestParam("categoryId") Integer cid){
		CategoryDto createCatDto = categoryService.updateCategory(categoryDto, cid);
		return new ResponseEntity<CategoryDto>(createCatDto ,HttpStatus.OK); 
	}
	
	@PostMapping("admin/deleteCategory")
	public ResponseEntity<ApiResponse> deleteCategory(@RequestParam("categoryId") Integer cid){
		categoryService.deleteCategory(cid) ; 
		return  new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted",1 ,true) ,HttpStatus.OK); 
	}
	
	@GetMapping("admin/getCategoryById")  
	public ResponseEntity<CategoryDto> getcategoryById(@RequestParam("categoryId") Integer cid){
		CategoryDto catDto = categoryService.getCategory(cid);
		return new ResponseEntity<CategoryDto>(catDto ,HttpStatus.OK);     
	}
	@GetMapping("call/getCategories")
	public ResponseEntity<List<CategoryDto>> getcategoryById(){
		List<CategoryDto> catDto = categoryService.getCategories();
		return ResponseEntity.ok(catDto);   
	}
	
}
