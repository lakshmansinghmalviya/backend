package com.example.quizapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.AuthRequest;
import com.example.quizapp.dto.AuthResponse;
import com.example.quizapp.dto.RegisterUser;

@Service
public class AuthService {

	public ResponseEntity<AuthResponse> register(RegisterUser registerUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
		// TODO Auto-generated method stub
		return null;
	}
  
}
