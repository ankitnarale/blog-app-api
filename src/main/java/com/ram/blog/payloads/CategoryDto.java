package com.ram.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer id;
	
	@NotEmpty
	@Size(min = 10, message = "Title must be minimum 10 charectors")
	private String categoryTitle;
	
	@NotEmpty
	@Size(max = 500, message = "Blog must be of 500 charectors!")
	private String categoryDiscription;
}
