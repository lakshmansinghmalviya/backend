package com.example.quizapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.LimitedUsersRequest;
import com.example.quizapp.dto.LimitedUsersResponse;
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

	@GetMapping("/byRole/{role}")
	public ResponseEntity<List<MyUser>> getUserProfileByRole(@PathVariable("role") String role) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfileByRole(role));
	}

	@PostMapping("/top")
	public ResponseEntity<Page<LimitedUsersResponse>> getLimitedUsersByRole(@RequestBody LimitedUsersRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getEducators(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MyUser> updateUserById(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request) {
		MyUser user = userService.updateUserById(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PutMapping("/logout/{id}")
	public ResponseEntity<Void> doUserLogout(@PathVariable Long id) {
		userService.logout(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
