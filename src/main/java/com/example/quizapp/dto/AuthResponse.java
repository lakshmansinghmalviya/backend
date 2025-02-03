package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class AuthResponse {
	private String token;
	private String role;
	private Boolean isApproved;

	public AuthResponse(String token, String role, Boolean isApproved) {
		super();
		this.token = token;
		this.role = role;
		this.isApproved = isApproved;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public AuthResponse() {
		super();
	}
}