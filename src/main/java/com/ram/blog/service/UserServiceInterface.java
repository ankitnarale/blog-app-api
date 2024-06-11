package com.ram.blog.service;

import java.util.List;

import com.ram.blog.payloads.UserDto;

public interface UserServiceInterface {

	UserDto registerUser(UserDto userDto);

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUser();

	void deleteUser(Integer userId);

}
