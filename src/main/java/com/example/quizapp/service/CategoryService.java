//package com.example.quizapp.service;
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.quizapp.dto.CategoryRequest;
//import com.example.quizapp.dto.CategoryUpdateRequest;
//import com.example.quizapp.dto.MessageResponse;
//import com.example.quizapp.entity.Category;
//import com.example.quizapp.entity.User;
//import com.example.quizapp.exception.ResourceNotFoundException;
//import com.example.quizapp.repository.CategoryRepository;
//import com.example.quizapp.repository.UserRepository;
//
//@Service
//public class CategoryService {
//
//	@Autowired
//	private CategoryRepository categoryRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	public MessageResponse createCategory(CategoryRequest request) {
//		try {
//			User creator = userRepository.findById(request.getCreatorId())
//					.orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//			Category category = new Category();
//			category.setName(request.getName());
//			category.setDescription(request.getDescription());
//			category.setActive(true);
//			category.setCategoryPic(request.getCategoryPic());
//			category.setCreator(creator);
//			categoryRepository.save(category);
//			return new MessageResponse("Category Created");
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to create category: " + e.getMessage());
//		}
//	}
//
//	@Transactional
//	public List<Category> getCategoriesByCreatorId(Long creatorId) {
//		try {
//			return categoryRepository.findByCreator_UserId(creatorId);
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to retrieve categories: " + e.getMessage());
//		}
//	}
//
//	@Transactional
//	public String deleteCategoryById(Long categoryId) {
//		try {
//			if (categoryRepository.existsById(categoryId))
//				categoryRepository.deleteById(categoryId);
//
//			return "Deleted the category Successfully";
//
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to delete category: " + e.getMessage());
//		}
//	}
//
//	@Transactional
//	public Category updateCategoryById(Long categoryId, CategoryUpdateRequest request) {
//		try {
//			Category category = categoryRepository.findById(categoryId)
//					.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
//			category.setName(request.getName());
//			category.setDescription(request.getDescription());
//			category.setCategoryPic(request.getCategoryPic());
//			return categoryRepository.save(category);
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to update category: " + e.getMessage());
//		}
//	}
//
//	public Category getCategoryById(Long categoryId) {
//		try {
//			Category category = categoryRepository.findById(categoryId)
//					.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
//			return category;
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to retrieve category: " + e.getMessage());
//		}
//	}
//
//	public Long getTotalCategory(Long id) {
//		try {
//			return categoryRepository.countByCreator_UserId(id);
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to fetch total category of the user: " + e.getMessage());
//		}
//	}
//
//	public List<Category> getCategories() {
//		try {
//			return categoryRepository.findAll();
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to retrieve categories: " + e.getMessage());
//		}
//	}
//}
