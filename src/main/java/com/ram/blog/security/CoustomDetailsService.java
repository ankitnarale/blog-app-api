package com.ram.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ram.blog.entities.User;
import com.ram.blog.exceptions.ResourceNotFoundException;
import com.ram.blog.repositories.UserRepo;

@Service
public class CoustomDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Loading Username(email) and Password from database;
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User of "+username+" not found", null));
		
		
		return user;
	}

}
