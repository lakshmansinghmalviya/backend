package com.example.quizapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.CategoryRepository;
import com.example.quizapp.util.CommonHelper;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserService userService;

	@Autowired
	CommonHelper commonHelper;

	public UnifiedResponse<?> createCategory(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setCategoryPic(request.getCategoryPic());
		category.setCreator(getUser());
		categoryRepository.save(category);
		return new UnifiedResponse(HttpStatus.OK.value(), "Category Created Successfully", null);
	}

	public List<Category> getCategoriesByCreatorId(Long creatorId) {
		return categoryRepository.findByCreatorId(creatorId);
	}

	public void deleteCategoryById(Long categoryId) {
		if (!categoryRepository.existsById(categoryId)) {
			throwException(categoryId);
		}
		categoryRepository.deleteById(categoryId);
	}

	public Category updateCategoryById(Long categoryId, CategoryRequest request) {

		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> throwException(categoryId));
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setCategoryPic(request.getCategoryPic());
		return categoryRepository.save(category);
	}

	public Category getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(() -> throwException(categoryId));
	}

	public UnifiedResponse<PageResponse<Category>> getCategories() {
		int page = 0;
		int size = Integer.MAX_VALUE;
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Category> categoryPage = categoryRepository.findAll(pageRequest);
		return commonHelper.getPageResponse(categoryPage);
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Category not found with the id " + id);
	}

	public UnifiedResponse<PageResponse<Category>> getCategoriesByPagination(Pageable pageable) {
		Page<Category> categoryPage = categoryRepository.findByCreatorId(getUser().getId(), pageable);
		return commonHelper.getPageResponse(categoryPage);
	}

	public UnifiedResponse<PageResponse<Category>> getCategoriesByPaginationBetweenDates(String startDate,
			String endDate, Pageable pageable) {

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
		LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);
		Page<Category> categoryPage = categoryRepository.findCategoriesByCreatorIdAndBetweenDates(getUser().getId(),
				startDateTime, endDateTime, pageable);

		return commonHelper.getPageResponse(categoryPage);
	}

	public UnifiedResponse<PageResponse<Category>> searchCategoriesByQuery(String query, Pageable pageable) {
		Page<Category> categories = categoryRepository
				.findByCreatorIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(getUser().getId(), query,
						query, pageable);
		return commonHelper.getPageResponse(categories);
	}

	public User getUser() {
		return commonHelper.getUser();
	}
}
