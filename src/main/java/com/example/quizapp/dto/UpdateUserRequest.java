package com.example.quizapp.dto;

import com.example.quizapp.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequest {

	@NotBlank(message = "Name is required.")
	@Size(min = 3, max = 100, message = "Name should have between 3 and 100 characters.")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]*$", message = "Name should start with a letter and should not contain special characters.")
	private String name;

	private String password;

	private String profilePic;

	private String education;

	private String bio;

	@Enumerated(EnumType.STRING)
	private Role role;

	private Boolean isApproved;
}