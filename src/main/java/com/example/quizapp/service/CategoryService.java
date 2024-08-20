package com.example.quizapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.CategoryUpdateRequest;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.repository.CategoryRepository;
import com.example.quizapp.repository.UserRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	public String createCategory(CategoryRequest request) {
		try {
			MyUser creator = userRepository.findById(request.getCreatorId())
					.orElseThrow(() -> new RuntimeException("User not found"));
			Category category = new Category();
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			category.setActive(true);
			category.setCreator(creator);
			categoryRepository.save(category);
			return "Category Created!";
		} catch (Exception e) {
			throw new RuntimeException("Failed to create category: " + e.getMessage());
		}
	}

	public List<Category> getCategoriesByCreatorId(Long creatorId) {
		try {
			List<Category> categories = categoryRepository.findByCreatorId(creatorId);
			return categories.stream().peek(category -> category.setCreator(null)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve categories: " + e.getMessage());
		}
	}

	public String deleteCategoryById(Long categoryId) {
		try {
			if (categoryRepository.existsById(categoryId)) {
				categoryRepository.deleteById(categoryId);
				return "Deleted the category Successfully";
			} else {
				throw new RuntimeException("Category not found");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete category: " + e.getMessage());
		}
	}

	public String updateCategoryById(Long categoryId, CategoryUpdateRequest request) {
		try {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new RuntimeException("Category not found"));
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			categoryRepository.save(category);
			return "Updated the category Successfully";
		} catch (Exception e) {
			throw new RuntimeException("Failed to update category: " + e.getMessage());
		}
	}

	public Category getCategoryById(Long categoryId) {
		try {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new RuntimeException("Category not found"));
			category.setCreator(null);
			return category;
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve category: " + e.getMessage());
		}
	}
}
