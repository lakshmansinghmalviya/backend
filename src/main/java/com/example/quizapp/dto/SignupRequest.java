package com.example.quizapp.dto;

import com.example.quizapp.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class SignupRequest {

	@NotBlank(message = "Name is required.")
	@Size(min = 3, max = 100, message = "Name should have between 3 and 100 characters.")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]*$", message = "Name should start with a letter and should not contain special characters.")
	private String name;

	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Please enter a valid email address.")
	@Size(min = 10, max = 100, message = "Email should have at least 10 characters and at most 100 characters")
	@Pattern(regexp = "[a-z0-9._]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Please enter a valid email address.")
	private String email;

	@NotBlank(message = "Password is required.")
	@Size(min = 8, max = 100, message = "Password should have between 8 and 100 characters.")
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Password must be at least 8 characters long and include at least one number, one uppercase letter, and one lowercase letter.")
	private String password;

	@NotNull(message = "Role is required.")
	private Role role;

	private String profilePic;

	private String education;

	private String bio;

	public SignupRequest() {
		super();
	}

	public SignupRequest(
			@NotBlank(message = "Name is required.") @Size(min = 3, max = 100, message = "Name should have between 3 and 100 characters.") @Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]*$", message = "Name should start with a letter and should not contain special characters.") String name,
			@NotBlank(message = "Email cannot be empty") @Email(message = "Please enter a valid email address.") @Size(min = 10, max = 100, message = "Email should have at least 10 characters and at most 100 characters") @Pattern(regexp = "[a-z0-9._]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Please enter a valid email address.") String email,
			@NotBlank(message = "Password is required.") @Size(min = 8, max = 100, message = "Password should have between 8 and 100 characters.") @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "Password must be at least 8 characters long and include at least one number, one uppercase letter, and one lowercase letter.") String password,
			@NotNull(message = "Role is required.") Role role, String profilePic, String education, String bio) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.profilePic = profilePic;
		this.education = education;
		this.bio = bio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
