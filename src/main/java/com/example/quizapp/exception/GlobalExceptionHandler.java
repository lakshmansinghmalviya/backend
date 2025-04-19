package com.example.quizapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.quizapp.dto.response.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MessageResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		MessageResponse errorResponse = new MessageResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		MessageResponse errorResponse = new MessageResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<MessageResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		MessageResponse errorResponse = new MessageResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(ApprovalPendingException.class)
	public ResponseEntity<MessageResponse> handleApprovalPendingException(ApprovalPendingException ex) {
		MessageResponse res = new MessageResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

	@ExceptionHandler(TokenNotValidException.class)
	public ResponseEntity<MessageResponse> handleTokenNotValidException(TokenNotValidException ex) {
		MessageResponse res = new MessageResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<MessageResponse> handleRuntimeException(RuntimeException ex) {
		MessageResponse res = new MessageResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}
}
