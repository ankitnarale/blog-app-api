package com.ram.blog.service;

import java.util.List;

import com.ram.blog.payloads.CategoryDto;

public interface CategoryServiceInterface {

//	create and update
	public CategoryDto createCategory(CategoryDto categoryDto);
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
//	delete
	public void deleteCategory(Integer id);
	
//	get all and get by id
	public List<CategoryDto> getAllCategory();
	public CategoryDto	getByIdCategory(Integer categoryId);
	
	
}
