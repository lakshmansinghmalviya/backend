package com.example.quizapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.entity.User;
import com.example.quizapp.service.UserService;

@Service
public class UserHelper {
	@Autowired
	UserService userService;

	public User getUser() {
		return userService.getUserInfoUsingTokenInfo();
	}
}
