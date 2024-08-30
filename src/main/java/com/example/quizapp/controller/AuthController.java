package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.AuthRequest;
import com.example.quizapp.dto.AuthResponse;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.RegisterUser;
import com.example.quizapp.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterUser registerUser) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.register(registerUser));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.login(authRequest));
	}
}
