package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.quizapp.dto.UpdateUserRequest;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Page<MyUser> getEducators(String role,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByRole(role, pageable);
    }
	
	public MyUser getUser(Long id) {
		try {
			return userRepository.findByUserId(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User Not Found" + e.getMessage());
		}
	}

	public MyUser updateUserById(Long id, UpdateUserRequest request) {
		try {
			MyUser user = userRepository.findByUserId(id);
			user.setBio(request.getBio());
			user.setName(request.getName());
			user.setProfilePic(request.getProfilePic());
			user.setUsername(request.getUsername());
			if (!request.getPassword().trim().isEmpty())
				user.setPassword(passwordEncoder.encode(request.getPassword()));
			return userRepository.save(user);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User Not Found" + e.getMessage());
		}
	}

	public void logout(Long id) {
		try {
			MyUser user = userRepository.findByUserId(id);
			user.setToken(null);
			userRepository.save(user);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User Not Found" + e.getMessage());
		}
	}
}
