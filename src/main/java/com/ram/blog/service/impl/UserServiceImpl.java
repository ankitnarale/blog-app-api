package com.ram.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ram.blog.entities.Roles;
import com.ram.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.entities.User;
import com.ram.blog.exceptions.ResourceNotFoundException;
import com.ram.blog.payloads.UserDto;
import com.ram.blog.repositories.UserRepo;
import com.ram.blog.service.UserServiceInterface;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passEncoder;

    @Autowired
    private RoleRepo roleRepo;


    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passEncoder.encode(userDto.getPassword()));
        Roles roles = this.roleRepo.findById(ApiConstants.NORMAL_ROLE).get();
        user.getRoles().add(roles);
        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    //	Create user
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);
//        user.setPassword(passEncoder.encode(userDto.getPassword()));
        User savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    //	Update user
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND, userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());

        User updateduser = this.userRepo.save(user);


        return this.modelMapper.map(updateduser, UserDto.class);
    }

    //	Get user by id
    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND, userId));

        return this.modelMapper.map(user, UserDto.class);
    }

    //	Get all user
    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return userDtos;
    }

    //	Delete User
    @Override
    public void deleteUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstants.USER_NOT_FOUND, userId));

        this.userRepo.delete(user);

    }

//	private User dtoToUser(UserDto userDto) {
//		User user = this.modelMapper.map(userDto, User.class);
//		return user;
//
//	}

//	public UserDto userToDto(User user) {
//		UserDto userDto = this.modelMapper.map(user	, UserDto.class);
//		
////		userDto.setId(user.getId());
////		userDto.setName(user.getName());
////		userDto.setEmail(user.getEmail());
////		userDto.setPassword(user.getPassword());
////		userDto.setAbout(user.getAbout());
//		return userDto;
//	}

}
