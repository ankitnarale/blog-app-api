package com.ram.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.ram.blog.entities.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class UserDto {

	private Integer id;

	@NotEmpty
	@Size(min = 3, message = "User name must be more than 3 characters")
	private String name;

	@Email(message = "Invalid email! Pls try again")
	private String email;

	@NotEmpty
	@Size(min = 3, message = "Week Password")
	@Pattern(regexp = "[a-zA-Z0-9_]+", message = "must not contain Symbols !")
	private String password;

	@NotNull
	private String about;

	private Set<CommentDto> comments = new HashSet<>();

	private Set<Roles> roles = new HashSet<>();

}
