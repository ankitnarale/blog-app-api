package com.ram.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.entities.Category;
import com.ram.blog.exceptions.ResourceNotFoundException;
import com.ram.blog.payloads.CategoryDto;
import com.ram.blog.repositories.CategoryRepo;
import com.ram.blog.service.CategoryServiceInterface;

@Service
public class CategoryServiceImpl implements CategoryServiceInterface {

	@Autowired
	private CategoryRepo catRepo;

	@Autowired
	private ModelMapper modelMapper;

//	Create Category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = catRepo.save(cat);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

//	Update Category
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND, categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDiscription(categoryDto.getCategoryDiscription());
		Category save = this.catRepo.save(category);

		return this.modelMapper.map(save, CategoryDto.class);
	}

//	Delete Category

	@Override
	public void deleteCategory(Integer id) {
		this.catRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND, id));

		this.catRepo.deleteById(id);

	}

//	Get all Category
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> all = this.catRepo.findAll();
		List<CategoryDto> collect = all.stream().map(cat -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return collect;
	}

//	Get Category by id
	@Override
	public CategoryDto getByIdCategory(Integer categoryId) {
		Category category = this.catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(ApiConstants.CATEGORY_NOT_FOUND, categoryId));

		return this.modelMapper.map(category, CategoryDto.class);
	}

}
