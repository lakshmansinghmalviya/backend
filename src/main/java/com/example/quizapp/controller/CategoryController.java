package com.example.quizapp.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.service.CategoryService;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.ResponseBuilder;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CommonHelper commonHelper;

	@PostMapping()
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UnifiedResponse<Category>> createCategory(@Valid @RequestBody CategoryRequest request) {
		return ResponseBuilder.buildResponse(HttpStatus.CREATED, categoryService.createCategory(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UnifiedResponse<Category>> updateCategoryById(@PathVariable Long id,
			@Valid @RequestBody CategoryRequest request) {
		return ResponseBuilder.buildOKResponse(categoryService.updateCategoryById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<UnifiedResponse<Void>> deleteCategory(@PathVariable("id") Long id) {
		return ResponseBuilder.buildOKResponse(categoryService.deleteCategoryById(id));
	}

	@GetMapping("/filters")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> filterCategories(
			@RequestParam(required = false) String query, @RequestParam(required = false) Long creatorId,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String start,
			@RequestParam(required = false) String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseBuilder.buildOKResponse(categoryService.filterCategories(query, start, end, creatorId, sort,
				commonHelper.makePageReq(page, size)));
	}
}
