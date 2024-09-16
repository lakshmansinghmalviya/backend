package com.example.quizapp.controller;

import java.util.List;

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
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<MessageResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(request));
	}

	@GetMapping("/ofCreator")
	public ResponseEntity<List<Category>> getCategoriesOfCreator() {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoriesOfCreator());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Category> updateCategoryById(@PathVariable("id") Long id,
			@Valid @RequestBody CategoryRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategoryById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}


	@GetMapping()
	public ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
	}
}
