package com.example.quizapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.AuthResponse;
import com.example.quizapp.dto.LoginRequest;
import com.example.quizapp.dto.SignupRequest;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceAlreadyExistsException;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.JwtHelper;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private MyUserDetailService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommonHelper commonHelper;

	public UnifiedResponse<AuthResponse> register(SignupRequest registerUser) {
		if (userRepository.existsByEmail(registerUser.getEmail())) {
			throw new ResourceAlreadyExistsException("User already exists. Please try with other credentials.");
		}

		User user = new User();
		user.setName(registerUser.getName());
		user.setEmail(registerUser.getEmail());
		user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
		user.setRole(registerUser.getRole());
		user.setProfilePic(registerUser.getProfilePic());
		user.setBio(registerUser.getBio());
		user.setLogout(false);
		user.setIsDeleted(false);
		user.setLastLogin(LocalDateTime.now());
		user.setEducation(registerUser.getEducation());
		userRepository.save(user);
		return login(new LoginRequest(registerUser.getEmail(), registerUser.getPassword()));
	}

	public UnifiedResponse<AuthResponse> login(LoginRequest authRequest) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
		String token = jwtHelper.generateToken(userDetails);

		User user = userRepository.findByEmail(authRequest.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("Email " + authRequest.getEmail() + " not found"));

		user.setLastLogin(LocalDateTime.now());
		user.setLogout(false);
		userRepository.save(user);
		return commonHelper.returnUnifiedOK("Auth Success", new AuthResponse(token, user.getRole().name(),user.getIsApproved()));
	}
}
