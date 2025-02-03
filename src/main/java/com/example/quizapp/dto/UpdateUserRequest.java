package com.example.quizapp.dto;

import com.example.quizapp.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public UpdateUserRequest(
			@NotBlank(message = "Name is required.") @Size(min = 3, max = 100, message = "Name should have between 3 and 100 characters.") @Pattern(regexp = "^[A-Za-z][A-Za-z0-9 ]*$", message = "Name should start with a letter and should not contain special characters.") String name,
			String password, String profilePic, String education, String bio, Role role, Boolean isApproved) {
		super();
		this.name = name;
		this.password = password;
		this.profilePic = profilePic;
		this.education = education;
		this.bio = bio;
		this.role = role;
		this.isApproved = isApproved;
	}

	public UpdateUserRequest() {
		super();
	}

}