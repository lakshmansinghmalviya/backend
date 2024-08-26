package com.example.quizapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.CategoryUpdateRequest;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.MyUser;
import com.example.quizapp.repository.CategoryRepository;
import com.example.quizapp.repository.UserRepository;

@Service
public class CategoryService {

	Logger log = LoggerFactory.getLogger(CategoryService.class);
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	public String createCategory(CategoryRequest request) {
		try {
			MyUser creator = userRepository.findById(request.getCreatorId())
					.orElseThrow(() -> new RuntimeException("User not found"));

			log.info("UserInformation from database {} ", creator.getUserId());
			log.info("UserInformation from database {} ", creator.getName());
			log.info("UserInformation from database {} ", creator.getUsername());
			log.info("UserInformation from database {} ", creator.getProfilePic());

			log.info("UserInformation from database {} ", creator.getUserId());
			log.info("UserInformation from database {} ", creator.getUserId());
			Category category = new Category();
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			category.setActive(true);
			category.setCategoryPic(request.getCategoryPic());
			category.setCreator(creator);

			Category savedCategory = categoryRepository.save(category);
			log.info("Saved Category: {}-------------------------------", savedCategory.getCategoryPic());

			log.info("Save in category UserInformation from database {} ", creator.getUserId());
			log.info("Save in category UserInformation from database {} ", creator.getName());
			log.info("Save in category UserInformation from database {} ", creator.getUsername());
			log.info("Save in category UserInformation from database {} ", creator.getProfilePic());

			return "Category Created!";
		} catch (Exception e) {
			throw new RuntimeException("Failed to create category: " + e.getMessage());
		}
	}

	@Transactional
	public List<Category> getCategoriesByCreatorId(Long creatorId) {
		try {
			List<Category> categories = categoryRepository.findByCreator_UserId(creatorId);
			return categories;
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve categories: " + e.getMessage());
		}
	}

	@Transactional
	public String deleteCategoryById(Long categoryId) {
		try {
			if (categoryRepository.existsById(categoryId))
				categoryRepository.deleteById(categoryId);

			return "Deleted the category Successfully";

		} catch (Exception e) {
			throw new RuntimeException("Failed to delete category: " + e.getMessage());
		}
	}

	@Transactional
	public String updateCategoryById(Long categoryId, CategoryUpdateRequest request) {
		try {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new RuntimeException("Category not found"));
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			category.setCategoryPic(request.getCategoryPic());
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
			return category;
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve category: " + e.getMessage());
		}
	}
}
