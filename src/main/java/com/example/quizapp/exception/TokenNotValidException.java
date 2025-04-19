package com.example.quizapp.exception;

public class TokenNotValidException extends RuntimeException {
	public TokenNotValidException(String msg) {
		super(msg);
	}
}
