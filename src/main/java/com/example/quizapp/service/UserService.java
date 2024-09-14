package com.example.quizapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.LimitedUsersRequest;
import com.example.quizapp.dto.LimitedUsersResponse;
import com.example.quizapp.dto.UpdateUserRequest;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.util.JwtHelper;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Page<LimitedUsersResponse> getEducators(LimitedUsersRequest request) {
		try {
			Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
			Page<User> usersPage = userRepository.findByRole(request.getRole(), pageable);
			return usersPage.map(user -> {
				LimitedUsersResponse response = new LimitedUsersResponse();
				response.setName(user.getName());
				response.setProfilePic(user.getProfilePic());
				response.setBio(user.getBio());
				return response;
			});
		} catch (Exception e) {
			throw new RuntimeException("Error fetching educators: " + e.getMessage(), e);
		}
	}

	public User getUser(Long id) {
		try {
			return userRepository.findByUserId(id)
					.orElseThrow(() -> new ResourceNotFoundException("User Not Found with ID: " + id));
		} catch (Exception e) {
			throw new ResourceNotFoundException("Error fetching user with ID: " + id + " " + e.getMessage());
		}
	}

	public User getUserByEmail(String email) {
		try {
			return userRepository.findByEmail(email)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		} catch (Exception e) {
			throw new ResourceNotFoundException("Error fetching user with email: " + email + " " + e.getMessage());
		}
	}

	public List<User> getUserProfileByRole(String role) {
		try {
			List<User> users = userRepository.findByRole(role);
			if (users.isEmpty())
				throw new ResourceNotFoundException("No users found with role: " + role);
			return users;
		} catch (Exception e) {
			throw new ResourceNotFoundException("Error fetching users with role: " + role + " " + e.getMessage());
		}
	}

	public User updateUserById(Long id, UpdateUserRequest request) {
		try {
			User user = userRepository.findByUserId(id)
					.orElseThrow(() -> new ResourceNotFoundException("User Not Found with ID: " + id));
			user.setBio(request.getBio());
			user.setName(request.getName());
			user.setProfilePic(request.getProfilePic());
			user.setEducation(request.getEducation());
			if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
				user.setPassword(passwordEncoder.encode(request.getPassword()));
			}
			return userRepository.save(user);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Error updating user with ID: " + id + " " + e.getMessage());
		}
	}

	public void logout(Long id) {
		try {
			User user = userRepository.findByUserId(id)
					.orElseThrow(() -> new ResourceNotFoundException("User Not Found with ID: " + id));
			user.setLogout(true);
			userRepository.save(user);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Error logging out user with ID: " + id + " " + e.getMessage());
		}
	}

	public User getUserInfoUsingContextHolder() {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return getUserByEmail(username);
	}
}
