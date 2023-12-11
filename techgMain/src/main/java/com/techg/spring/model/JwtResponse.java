package com.techg.spring.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.techg.spring.payload.UserDto;
import com.techg.spring.payload.UserDtoRes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
  
	private String token;
	private UserDtoRes user ;
	private Collection<? extends GrantedAuthority> roles;
}
