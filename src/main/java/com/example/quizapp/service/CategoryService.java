package com.example.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.CategoryRequest;
import com.example.quizapp.dto.MessageResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
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
		User creator = userService.getUserInfoUsingTokenInfo();
		Category category = new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setCategoryPic(request.getCategoryPic());
		category.setCreator(creator);
		categoryRepository.save(category);
		return new MessageResponse("Category Created");
	}

	public List<Category> getCategoriesByCreatorId(Long creatorId) {
		return categoryRepository.findByCreatorId(creatorId);
	}

	public List<Category> getCategoriesOfCreator() {
		User creator = userService.getUserInfoUsingTokenInfo();
		return categoryRepository.findByCreatorId(creator.getId());
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

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public ResourceNotFoundException throwException(Long id) {
		throw new ResourceNotFoundException("Category not found with the id " + id);
	}

	public UnifiedResponse<PageResponse<Category>> getCategoriesByPagination(Pageable pageable) {
		User creator = userService.getUserInfoUsingTokenInfo();
		Page<Category> categoryPage = categoryRepository.findByCreatorId(creator.getId(), pageable);
		PageResponse<Category> pageResponse = new PageResponse<>();
		pageResponse.setContent(categoryPage.getContent());
		pageResponse.setPageNumber(categoryPage.getNumber());
		pageResponse.setPageSize(categoryPage.getSize());
		pageResponse.setTotalElements(categoryPage.getTotalElements());
		pageResponse.setTotalPages(categoryPage.getTotalPages());
		UnifiedResponse<PageResponse<Category>> response = new UnifiedResponse<>(HttpStatus.OK.value(),
				"Fetched the category Successfully", pageResponse);
		return response;
	}
}
