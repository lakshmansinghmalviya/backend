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
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Category>> createCategory(@Valid @RequestBody CategoryRequest request) {
		return ResponseBuilder.buildResponse(HttpStatus.CREATED, categoryService.createCategory(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Category>> updateCategoryById(@PathVariable("id") Long id,
			@Valid @RequestBody CategoryRequest request) {
		return ResponseBuilder.buildOKResponse(categoryService.updateCategoryById(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('Educator')")
	public ResponseEntity<UnifiedResponse<Void>> deleteCategory(@PathVariable("id") Long id) {
		return ResponseBuilder.buildOKResponse(categoryService.deleteCategoryById(id));
	}

	@GetMapping("/student")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> getCategories(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder.buildOKResponse(categoryService.getCategories(commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/ofCreator")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> getCategoriesByPagination(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int size) {
		return ResponseBuilder
				.buildOKResponse(categoryService.getCategoriesByPagination(commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/ofCreator/betweenDates/{start}/{end}")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> getCategoriesBetweenDates(@PathVariable String start,
			@PathVariable String end, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder.buildOKResponse(categoryService.getCategoriesByPaginationBetweenDates(start, end,
				commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> searchCategories(@RequestParam String query,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder
				.buildOKResponse(categoryService.searchCategoriesByQuery(query, commonHelper.makePageReq(page, size)));
	}

	@GetMapping("/student/search")
	public ResponseEntity<UnifiedResponse<PageResponse<Category>>> searchCategoriesForStudent(
			@RequestParam String query, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseBuilder.buildOKResponse(
				categoryService.searchCategoriesForStudent(query, commonHelper.makePageReq(page, size)));
	}
}
