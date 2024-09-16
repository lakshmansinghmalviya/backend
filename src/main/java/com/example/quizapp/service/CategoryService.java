package com.example.quizapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.entity.Category;
import com.example.quizapp.entity.User;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserService userService;

	public MessageResponse createCategory(CategoryRequest request) {
		try {
			User creator = userService.getUserInfoUsingTokenInfo();
			Category category = new Category();
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			category.setCategoryPic(request.getCategoryPic());
			category.setCreator(creator);
			categoryRepository.save(category);
			return new MessageResponse("Category Created");
		} catch (Exception e) {
			throw new RuntimeException("Failed to create category: " + e.getMessage());
		}
	}

	 
	public List<Category> getCategoriesByCreatorId(Long creatorId) {
		try {
			return categoryRepository.findByCreatorId(creatorId);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve categories: " + e.getMessage());
		}
	}

	 
	public List<Category> getCategoriesOfCreator() {
		try {
			User creator = userService.getUserInfoUsingTokenInfo();
			return categoryRepository.findByCreatorId(creator.getId());
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve categories: " + e.getMessage());
		}
	}

	public String deleteCategoryById(Long categoryId) {
		try {
			if (categoryRepository.existsById(categoryId))
				categoryRepository.deleteById(categoryId);

			return "Deleted the category Successfully";

		} catch (Exception e) {
			throw new RuntimeException("Failed to delete category: " + e.getMessage());
		}
	}

	public Category updateCategoryById(Long categoryId, CategoryRequest request) {
		try {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
			category.setName(request.getName());
			category.setDescription(request.getDescription());
			category.setCategoryPic(request.getCategoryPic());
			return categoryRepository.save(category);
		} catch (Exception e) {
			throw new RuntimeException("Failed to update category: " + e.getMessage());
		}
	}

	public Category getCategoryById(Long categoryId) {
		try {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
			return category;
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve category: " + e.getMessage());
		}
	}

	public List<Category> getCategories() {
		try {
			return categoryRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve categories: " + e.getMessage());
		}
	}
}
