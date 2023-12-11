package com.techg.spring.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techg.spring.model.Certificate;
import com.techg.spring.model.Role;
import com.techg.spring.model.User;
import com.techg.spring.payload.ApiResponse;
import com.techg.spring.payload.CertificateDto;
import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.payload.UserDto;
import com.techg.spring.repositories.RoleRepo;
import com.techg.spring.repositories.UserRepo;
import com.techg.spring.services.UserService;
import com.techg.spring.exceptions.DuplicateEntry;
import com.techg.spring.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo ;
	
	@Autowired
	private RoleRepo roleRepo ;
	
	@Autowired
	private ModelMapper modelMapper ;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		
		
		User user= dtoToUser(userDto) ;
		Optional<User> dupUser = userRepo.findByEmail(userDto.getEmail());
		
		if(dupUser.isPresent()) {
			throw new DuplicateEntry("User"," email ",dupUser.get().getEmail());
		}
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        
        Set<Role> roles = new HashSet<>();
//      for (String roleName : userDto.getRoles()) {
//          Role role = roleRepo.findByName(roleName).orElseThrow(()->new ResourceNotFoundException("role", roleName));
//          roles.add(role);
//      }
		Role role = roleRepo.findByName("ROLE_NORMAL").orElseThrow(()->new ResourceNotFoundException("role", "NORMAL"));
		roles.add(role);
        user.setRoles(roles);
        
		User savedUser =  userRepo.save(user) ;   
		return userToDto(savedUser); 
	}
	

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
		
		Set<Role> roles = new HashSet<>();
//        for (String roleName : userDto.getRoles()) {
//            Role role = roleRepo.findByName(roleName).orElseThrow(()->new ResourceNotFoundException("role", roleName));
//            roles.add(role);
//        }
		Role role = roleRepo.findByName("NORMAL").orElseThrow(()->new ResourceNotFoundException("role", "NORMAL"));
		roles.add(role);
        user.setRoles(roles);
		
		User updatedUser = userRepo.save(user) ;
		UserDto userDto1 = userToDto(updatedUser);
		
		return userDto1;   
	}
	
	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
		return userToDto(user);
	}

	@Override
	public PagnationResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort=null;
		sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize) ;

		Page<User> pageCer = userRepo.findAll(p);

		List<User> userList = pageCer.getContent();
		List<UserDto> userDtos = userList.stream().map((item)-> modelMapper.map(item, UserDto.class)).collect(Collectors.toList());

		PagnationResponse paginationResponse = new PagnationResponse();
		paginationResponse.setContent(userDtos);
		paginationResponse.setPageNumber(pageCer.getNumber());
		paginationResponse.setPageSize(pageCer.getSize());
		paginationResponse.setTotalElements(pageCer.getTotalElements());
		paginationResponse.setTotalPages(pageCer.getTotalPages());
		paginationResponse.setLastPage(pageCer.isLast());
		return paginationResponse;
	}

	@Override
	public void deleteUser(Integer userId) throws Exception  {
		User user = userRepo.findById(userId)
					.orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
//		Optional<User>  user=userRepo.findById(userId);
//		
//		if(user.isPresent()) {
//			userRepo.deleteById(user.get().getId());
//		}else {
//			throw  new Exception("User not found");
//		}
		
		userRepo.delete(user);
	}
//	@Override
//	public void deleteUser(Integer userId) {
//		User user = null;
//		try {
//			user = userRepo.findById(userId).orElseThrow(()-> new Exception("hello from nadim"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		userRepo.delete(user);
//	}
//	
	private User dtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		User user = modelMapper.map(userDto, User.class);
		return user;
		
	}
	private UserDto userToDto (User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		UserDto userDto = modelMapper.map(user, UserDto.class) ;
		return userDto ;
	}


	

}
