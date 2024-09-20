package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.EducatorProfileDataResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UpdateUserRequest;
import com.example.quizapp.entity.User;
import com.example.quizapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/currentUser")
	public ResponseEntity<User> getUserInformation() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfoUsingTokenInfo());
	}

	@GetMapping("/educatorProfileData")
	public ResponseEntity<EducatorProfileDataResponse> getEducatorProfileInformation() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getEducatorProfileInformation());
	}

	@GetMapping("/educators")
	public ResponseEntity<List<User>> getEducators() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getEducators());
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@Valid @RequestBody UpdateUserRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(request));
	}

	@PutMapping("/logout")
	public ResponseEntity<Void> doUserLogout() {
//		hello checking
		userService.logout();
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/public")
	public ResponseEntity<PageResponse<User>> getEducatorsForPublic(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(userService.getEducatorsForPublic(pageable));
	}
}
