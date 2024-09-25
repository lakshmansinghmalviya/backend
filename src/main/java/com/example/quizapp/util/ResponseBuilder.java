package com.example.quizapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.quizapp.dto.UnifiedResponse;

public class ResponseBuilder {
	public static <T> ResponseEntity<UnifiedResponse<T>> buildResponse(HttpStatus status, UnifiedResponse<T> response) {
		return ResponseEntity.status(status).body(response);
	}

	public static <T> ResponseEntity<UnifiedResponse<T>> buildOKResponse(UnifiedResponse<T> response) {
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
