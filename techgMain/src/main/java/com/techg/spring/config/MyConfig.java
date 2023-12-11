package com.techg.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techg.spring.exceptions.ResourceNotFoundException;
import com.techg.spring.repositories.UserRepo;

@Configuration
class MyConfig {
	
	@Autowired
	private UserRepo userRepo ;
	
    @Bean
    public UserDetailsService userDetailsService() {
    	return username -> userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User" ,"email"));                  
        
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
