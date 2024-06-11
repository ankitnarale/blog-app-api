package com.ram.blog.service;

import java.util.List;

import com.ram.blog.payloads.PostDto;
import com.ram.blog.payloads.PostResponse;

public interface PostServiceInterface {

//	Create post
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	
//	Update Post
	PostDto updatePost(PostDto postDto, Integer postDtoId);
	
//	delete
	void deletePost(Integer postDtoId);
	
//	get all posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
//	get by id
	PostDto getById(Integer postDtoId);
	
//	get all by category
	PostResponse getPostByCategory(Integer postCategoryID, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir);
	
//	get all by user
	PostResponse getPostByUser(Integer postUserID, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
//	search post
	List<PostDto> searchPost(String keyword);
	
	
	
	
}
