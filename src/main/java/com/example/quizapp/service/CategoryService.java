package com.example.quizapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.CategoryRepository;
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

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Category not found with the id " + id);
	}

	public User getUser() {
		return userHelper.getUser();
	}

	public UnifiedResponse<PageResponse<Category>> filterCategories(String query, String startDate, String endDate,
			Long creatorId, String sort, Pageable pageable) {

		LocalDateTime[] dates = { null, null };

		if (startDate != null && endDate != null) {
			dates = commonHelper.parseDateRange(startDate, endDate);
		}

		if (sort != null) {
			Sort sorting = commonHelper.parseSortString(sort);
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);
		}

		Page<Category> categories = categoryRepository.findCategoriesByFilters(creatorId, dates[0], dates[1], query,
				pageable);
		return commonHelper.getPageResponse(categories);
	}
}
