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
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(request));
	}

	@GetMapping("/creator/{id}")
	public ResponseEntity<List<Category>> getCategoriesByCreatorId(@PathVariable("id") Long id) {
		List<Category> categories = categoryService.getCategoriesByCreatorId(id);
		if (categories.isEmpty())  
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
					
		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<Category> updateCategoryById(@PathVariable("id") Long id,
			@RequestBody CategoryUpdateRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategoryById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategoryById(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryById(id));
	}
	
	@GetMapping("/creator/{id}/{total}")
	public ResponseEntity<Long> getTotalNumberOfCategories(@PathVariable("id") Long id){
	  return ResponseEntity.status(HttpStatus.OK).body(categoryService.getTotalCategory(id));
	}
}
