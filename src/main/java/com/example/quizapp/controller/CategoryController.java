package com.example.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@PostMapping()
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<?>> createCategory(@Valid @RequestBody CategoryRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Category>> updateCategoryById(@PathVariable("id") Long id,
			@Valid @RequestBody CategoryRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategoryById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Void>> deleteCategory(@PathVariable("id") Long id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping()
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> getCategories() {
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
	}

	@GetMapping("/ofCreator")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> getCategoriesByPagination(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoriesByPagination(pageable));
	}

	@GetMapping("/ofCreator/betweenDates/{start}/{end}")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> getCategoriesBetweenDates(@PathVariable String start,
			@PathVariable String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK)
				.body(categoryService.getCategoriesByPaginationBetweenDates(start, end, pageable));
	}

	@GetMapping("/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> searchCategories(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.searchCategoriesByQuery(query, pageable));
	}
}
