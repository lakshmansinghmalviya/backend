package com.example.quizapp.service;


import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.AuthRequest;
import com.example.quizapp.dto.AuthResponse;
import com.example.quizapp.dto.RegisterUser;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.util.JwtService;

@Service
public class AuthService {
   Logger logger = LoggerFactory.getLogger(AuthService.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<AuthResponse> register(RegisterUser registerUser) {
		if (userRepository.existsByUsername(registerUser.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse("Username already exists.", null,null));
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
		return ResponseEntity.ok(new AuthResponse("User Registered Successfully", null,null));
	}

	public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
			MyUser user = userRepository.findByUsername(authRequest.getUsername())
					.orElseThrow(() -> new RuntimeException("User not found"));
			String token = jwtUtil.generateToken(userDetails);
			user.setToken(token);
			userRepository.save(user);
			AuthResponse authResponse = new AuthResponse("Login successful.", token, user.getUserId());

			return ResponseEntity.ok(authResponse);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new AuthResponse("Invalid username or password.", null,null));
		}
	}
}
