package com.example.quizapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.CategoryUpdateRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<MessageResponse> createCategory(@RequestBody CategoryRequest request) {
		String message = categoryService.createCategory(request);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
	}

	@GetMapping("/creator/{id}")
	public ResponseEntity<List<Category>> getCategoriesByCreatorId(@PathVariable("id") Long id) {
		List<Category> categories = categoryService.getCategoriesByCreatorId(id);
		if (categories.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else
			return ResponseEntity.ok(categories);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<MessageResponse> updateCategoryById(@PathVariable("id") Long id,
			@RequestBody CategoryUpdateRequest request) {
		String message = categoryService.updateCategoryById(id, request);
		return ResponseEntity.ok(new MessageResponse(message));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<MessageResponse> deleteCategory(@PathVariable("id") Long id) {
		String message = categoryService.deleteCategoryById(id);
		return ResponseEntity.ok(new MessageResponse(message));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
		Category category = categoryService.getCategoryById(id);
		return ResponseEntity.ok(category);
	}
}
