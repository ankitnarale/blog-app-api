package com.ram.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ram.blog.exceptions.AuthException;
import com.ram.blog.payloads.UserDto;
import com.ram.blog.security.CoustomDetailsService;
import com.ram.blog.security.JwtAuthRequest;
import com.ram.blog.security.JwtAuthResponse;
import com.ram.blog.security.JwtTokenHelper;
import com.ram.blog.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private CoustomDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtheHelper;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {

		this.doAuthentication(request.getEmail(), request.getPassword());

		UserDetails userByUsername = this.userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.jwtheHelper.generateToken(userByUsername);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUsername(userByUsername.getUsername());
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}

	private void doAuthentication(String email, String password) {

		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(email, password);
		try {

			this.authManager.authenticate(userToken);
		} catch (BadCredentialsException bce) {
			System.out.println("Invalid user name and password!!!!");
			throw new AuthException("Invalid username and password!!");
		}
	}

	@PostMapping("/regis")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {

		UserDto user = this.userServiceImpl.registerUser(userDto);
		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);

	}

}
