package com.example.quizapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quizapp.dto.AuthRequest;
import com.example.quizapp.dto.AuthResponse;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.RegisterUser;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.exception.ResourceAlreadyExits;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.util.JwtHelper;

@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public MessageResponse register(RegisterUser registerUser) {
		try {

			if (userRepository.existsByUsername(registerUser.getUsername())) {
				throw new ResourceAlreadyExits("User Already Exits please try with other credentials");
			}

			MyUser user = new MyUser();
			user.setName(registerUser.getName());
			user.setUsername(registerUser.getUsername());
			user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
			user.setRole(registerUser.getRole());
			user.setProfilePic(registerUser.getProfilePic());
			user.setBio(registerUser.getBio());
			user.setActive(true);
			user.setCreatedAt(LocalDateTime.now());
			user.setUpdatedAt(LocalDateTime.now());
			userRepository.save(user);
			return new MessageResponse("User Registered Successfully");

		} catch (Exception e) {
			throw new RuntimeException("Couldn't register please try again");
		}
	}

	public AuthResponse login(AuthRequest authRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
			MyUser user = userRepository.findByUsername(authRequest.getUsername())
					.orElseThrow(() -> new ResourceNotFoundException("User data not found"));

			String token = jwtHelper.generateToken(userDetails);
			user.setToken(token);
			user.setLastLogin(LocalDateTime.now());
			userRepository.save(user);
			return new AuthResponse(token, user.getUserId(), user.getRole());
		} catch (AuthenticationException e) {
			throw new ResourceNotFoundException("Invalid username or password.");
		}
	}
}
