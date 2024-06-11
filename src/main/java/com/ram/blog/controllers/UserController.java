package com.ram.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.constants.UriConstants;
import com.ram.blog.payloads.ApiResponse;
import com.ram.blog.payloads.UserDto;
import com.ram.blog.service.UserServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceInterface userInterface;

//	POST - create user
	/**
	 * @author Ankit Narale
	 * @apiNote This api is to create user 
	 * @since 11/04/2024
	 * @param user
	 * @return
	 */
	@PostMapping(value = UriConstants.USER_)
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {

		UserDto dto = this.userInterface.createUser(user);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);

	}

//	PUT- update user
	/**
	 * @author Ankit Narale
	 * @apiNote This api is to update user by id
	 * @since 11/04/2024
	 * @param userDto
	 * @param userId
	 * @return
	 */
	@PutMapping(value = UriConstants.USER_ID)
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {

		UserDto updateUser = this.userInterface.updateUser(userDto, userId);

		return new ResponseEntity<UserDto>(updateUser, HttpStatus.CREATED);
	}

//	DELETE- delete user
	/**
	 * @author Ankit Narale
	 * @apiNote This api is to delete user by id
	 * @since 11/04/2024
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = UriConstants.USER_ID)
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userInterface.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(ApiConstants.DELETE_USER, true), HttpStatus.OK);
	}

//	GET- get user
	/**
	 * @author Ankit Narale
	 * @apiNote This api is to get all users
	 * @since 11/04/2024
	 * @return
	 */
	@GetMapping(value = UriConstants.USER_)
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> allUser = this.userInterface.getAllUser();
		return new ResponseEntity<List<UserDto>>(allUser, HttpStatus.OK);
	}

//	 get by id
	/**
	 * @author Ankit Narale
	 * @apiNote This api is to get user by id
	 * @since 11/04/2024
	 * @param userId
	 * @return
	 */
	@GetMapping(value = UriConstants.USER_ID)
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
		UserDto userById = this.userInterface.getUserById(userId);
		return new ResponseEntity<UserDto>(userById, HttpStatus.OK);
	}
}
