package com.example.quizapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.CategoryRepository;
import com.example.quizapp.util.Codes;
import com.example.quizapp.util.CommonHelper;
import com.example.quizapp.util.UserHelper;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	CommonHelper commonHelper;

	@Autowired
	UserHelper userHelper;

	public UnifiedResponse<Category> createCategory(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setCategoryPic(request.getCategoryPic());
		category.setCreator(getUser());
		categoryRepository.save(category);
		return commonHelper.returnUnifiedCREATED("Category Created Successfully", null);
	}

	public UnifiedResponse<Void> deleteCategoryById(Long categoryId) {
		if (!categoryRepository.existsById(categoryId)) {
			throwException(categoryId);
		}
		categoryRepository.deleteById(categoryId);
		return commonHelper.returnUnifiedOK("Deleted", null);
	}

	public UnifiedResponse<Category> updateCategoryById(Long categoryId, CategoryRequest request) {

		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> throwException(categoryId));
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setCategoryPic(request.getCategoryPic());
		return commonHelper.returnUnifiedOK("Updated", categoryRepository.save(category));
	}

	public Category getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(() -> throwException(categoryId));
	}

	public UnifiedResponse<PageResponse<Category>> getCategories(Pageable pageable) {
		Page<Category> categoryPage = categoryRepository.findAll(pageable);
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
		LocalDateTime[] dates = commonHelper.parseDateRange(startDate, endDate);
		Page<Category> categoryPage = categoryRepository.findCategoriesByCreatorIdAndBetweenDates(getUser().getId(),
				dates[0], dates[1], pageable);

		return commonHelper.getPageResponse(categoryPage);
	}

	public UnifiedResponse<PageResponse<Category>> searchCategoriesByQuery(String query, Pageable pageable) {
		Page<Category> categories = categoryRepository
				.findByCreatorIdAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(getUser().getId(), query,
						query, pageable);
		return commonHelper.getPageResponse(categories);
	}

	public UnifiedResponse<PageResponse<Category>> searchCategoriesForStudent(String query, Pageable pageable) {
		Page<Category> categories = categoryRepository
				.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable);
		return commonHelper.getPageResponse(categories);
	}

	public User getUser() {
		return userHelper.getUser();
	}
}
