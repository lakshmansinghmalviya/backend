package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.quizapp.dto.UpdateUserRequest;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<MyUser> getUserProfile(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MyUser> updateUserById(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request) {
		MyUser user = userService.updateUserById(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
