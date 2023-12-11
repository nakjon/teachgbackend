package com.techg.spring.controllers;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techg.spring.exceptions.ResourceNotFoundException;
import com.techg.spring.model.JwtRequest;
import com.techg.spring.model.JwtResponse;
import com.techg.spring.model.User;
import com.techg.spring.payload.UserDto;
import com.techg.spring.payload.UserDtoRes;
import com.techg.spring.repositories.UserRepo;
import com.techg.spring.security.JwtHelper;
import com.techg.spring.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("techg/api/auth")
@CrossOrigin(maxAge = 3600)
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
	private UserService userServices ;
    
    @Autowired
	private UserRepo userRepo ;
    
    @Autowired
    private ModelMapper modelMapper ;

	//post creating user
	@PostMapping("/adduser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		System.out.println("gggggg");
		UserDto createUserDto  = userServices.createUser(userDto);
		return new ResponseEntity<>(createUserDto , HttpStatus.CREATED) ; 
	}
	
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());                                                                                      
        String token = this.helper.generateToken(userDetails);
        
            Collection<? extends GrantedAuthority> list = userDetails.getAuthorities()    ;    
        
            User user = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()-> new ResourceNotFoundException("User"," email ")) ;
            UserDtoRes userDto = new UserDtoRes();
            userDto.setName(user.getName());
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            JwtResponse response = JwtResponse.builder()                   
                .token(token)
                .user(userDto)
                .roles(userDetails.getAuthorities()).build(); 
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}

