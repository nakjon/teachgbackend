package com.techg.spring.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techg.spring.config.AppConstants;
import com.techg.spring.payload.ApiResponse;
import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.payload.UserDto;
import com.techg.spring.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("techg/api/")
public class UserController {
     
	@Autowired
	private UserService userServices ;

	//updating user
	@PostMapping("users/updateuser")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto ,@RequestParam("userId") Integer uid){
		UserDto createUserDto  = userServices.updateUser(userDto, uid);
		return  ResponseEntity.ok(createUserDto ) ; 
	}
	
	//delete
	@PostMapping("admin/deleteuser")
	public ResponseEntity<?> deleteUser(@RequestBody Map<String,Object> inputData ){
		
		Integer uid = (Integer) inputData.get("id") ;
		System.out.println(uid);
		Map<String, Object> response= new LinkedHashMap<>();
		try {
			userServices.deleteUser(uid);
			//using Map class 
			response.put("status", 1);
			response.put("data", new ApiResponse("user Deleted successfully", 1,true));
			return new  ResponseEntity<>(response , HttpStatus.OK) ;
			
			//or we can send the API response object directly
			//ApiResponse apiResponse = new ApiResponse("user Deleted successfully", 1);
			//return new  ResponseEntity<>(apiResponse , HttpStatus.OK) ;
		} catch (Exception ex) {
			System.out.println("-------2------"+ex.getMessage());
			return new  ResponseEntity<>(new ApiResponse(ex.getMessage(), 0,false) , HttpStatus.NOT_FOUND) ;
		}
		
	}
	
	@GetMapping("pagination/getUserList")
	public ResponseEntity<PagnationResponse> getAllUser(@RequestParam(value = "pageNumber" ,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER ,required = false) Integer pageNumber ,
			 @RequestParam(value = "pageSize" ,defaultValue = AppConstants.DEFAULT_PAGE_SIZE ,required = false) Integer pageSize ,
			 @RequestParam(value = "sortBy" ,defaultValue = AppConstants.DEFAULT_SORT_BY ,required = false) String sortBy ,
			 @RequestParam(value = "sortDir" ,defaultValue = AppConstants.DEFAULT_SORT_DIR ,required = false) String sortDir){
		
		PagnationResponse userlist = userServices.getAllUser(pageNumber,pageSize ,sortBy,sortDir) ;
		return ResponseEntity.ok(userlist) ; 
	} 
	
	@GetMapping("users/getUserById")
	public ResponseEntity<UserDto> getUserById(@RequestParam("userId") Integer uid){
		UserDto user = null ;
		user = userServices.getUserById(uid ) ;
		return ResponseEntity.ok(user) ;   
	} 
	
	
	
	
}

