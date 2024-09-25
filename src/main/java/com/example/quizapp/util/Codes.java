package com.example.quizapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class Codes {
	public static final int OK = HttpStatus.OK.value();
	public static final int NOTFOUND = HttpStatus.NOT_FOUND.value();
	public static final int CREATED = HttpStatus.CREATED.value();
}
