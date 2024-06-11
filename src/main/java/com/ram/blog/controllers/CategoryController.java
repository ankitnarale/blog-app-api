package com.ram.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ram.blog.constants.ApiConstants;
import com.ram.blog.payloads.ApiResponse;
import com.ram.blog.payloads.CategoryDto;
import com.ram.blog.service.impl.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl catServImpl;

//	Create Category
	/**
	 * @author Ankit Narale
	 * @apiNote This api is used to create category
	 * @since 11/04/2024
	 * @param catDto
	 * @return
	 */
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto) {

		CategoryDto categoryDto = catServImpl.createCategory(catDto);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.CREATED);

	}

//	Update Category
	/**
	 * @author Ankit Narale
	 * @apiNote This api is used to update category by id
	 * @since 11/04/2024
	 * @param catDto
	 * @param idDto
	 * @return
	 */
	@PutMapping("/{idDto}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto, @PathVariable Integer idDto) {

		CategoryDto categoryDto = catServImpl.updateCategory(catDto, idDto);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.CREATED);

	}

//	Delete Category
	/**
	 * @author Ankit Narale
	 * @apiNote This api is used to delete category by id
	 * @since 11/04/2024
	 * @param idDto
	 * @return
	 */
	@DeleteMapping("/{idDto}")
	public ResponseEntity<ApiResponse> deleteCategory(@Valid @PathVariable Integer idDto) {

		catServImpl.deleteCategory(idDto);
		ApiResponse apiResponse = new ApiResponse(ApiConstants.DELETE_CATEGORY, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

	}

//	Get all and By id
	/**
	 * @author Ankit Narale
	 * @apiNote This api is used to get all categories
	 * @since 11/04/2024
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> allCategory = catServImpl.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(allCategory, HttpStatus.FOUND);

	}

	/**
	 * @author Ankit Narale
	 * @apiNote This api is used to get category by id
	 * @since 11/04/2024
	 * @param idDto
	 * @return
	 */
	@GetMapping("/{idDto}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer idDto) {
		CategoryDto cat = catServImpl.getByIdCategory(idDto);
		return new ResponseEntity<CategoryDto>(cat, HttpStatus.FOUND);

	}

}
