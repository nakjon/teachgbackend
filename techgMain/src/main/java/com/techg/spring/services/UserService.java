package com.techg.spring.services;

import java.util.List;

import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.payload.UserDto;

public interface UserService {
    
	 UserDto createUser(UserDto user) ;
	 UserDto updateUser(UserDto user ,Integer userId) ;
	 UserDto getUserById(Integer Id) ;
	 PagnationResponse getAllUser(Integer pageNumber,Integer pageSize,String sortBy ,String sortDir);
	 void deleteUser(Integer userId) throws Exception;
}
