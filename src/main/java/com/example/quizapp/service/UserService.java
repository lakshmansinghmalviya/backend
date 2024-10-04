package com.example.quizapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.AdminProfileDataResponse;
import com.example.quizapp.dto.EducatorProfileDataResponse;
import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.dto.UpdateUserRequest;
import com.example.quizapp.entity.User;
import com.example.quizapp.enums.Role;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.CategoryRepository;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.repository.UserRepository;
import com.example.quizapp.util.CommonHelper;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommonHelper commonHelper;

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
	}

	public UnifiedResponse<User> updateUser(Long id, UpdateUserRequest request) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		if (request.getRole().name() != null)
			user.setRole(Role.valueOf(request.getRole().name()));

		user.setBio(request.getBio());
		user.setName(request.getName());
		user.setProfilePic(request.getProfilePic());
		user.setEducation(request.getEducation());
		if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		return commonHelper.returnUnifiedOK("Updated user", userRepository.save(user));
	}

	public UnifiedResponse<Void> logout() {
		User user = getUserInfoUsingTokenInfo();
		user.setLogout(true);
		userRepository.save(user);
		return commonHelper.returnUnifiedOK("Logged out", null);
	}

	public UnifiedResponse<Void> deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		user.setIsDeleted(true);
		userRepository.save(user);
		return commonHelper.returnUnifiedOK("Deleted user", null);
	}

	public User getUserInfoUsingTokenInfo() {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return getUserByEmail(username);
	}

	public UnifiedResponse<User> getUserInformation() {
		return commonHelper.returnUnifiedOK("Fetched user", getUserInfoUsingTokenInfo());
	}

	public UnifiedResponse<EducatorProfileDataResponse> getEducatorProfileInformation() {
		User creator = getUserInfoUsingTokenInfo();
		Long totalQuiz = quizRepository.countByCreatorId(creator.getId());
		Long totalQuestion = questionRepository.countByCreatorId(creator.getId());
		return commonHelper.returnUnifiedOK("Fetched data", new EducatorProfileDataResponse(totalQuiz, totalQuestion));
	}

	public UnifiedResponse<AdminProfileDataResponse> getAdminProfileInformation() {
		Long totalCategory = categoryRepository.countTotalCategory();
		Long totalUsers = userRepository.countTotalUsers();
		return commonHelper.returnUnifiedOK("Fetched data", new AdminProfileDataResponse(totalUsers, totalCategory));
	}

	public UnifiedResponse<PageResponse<User>> findUsersByFilters(Role role, String query, String startDate,
			String endDate, String sort, Pageable pageable) {

		LocalDateTime[] dates = { null, null };
		List<Role> roles = new ArrayList<>();

		if (role == null) {
			roles.add(Role.valueOf("Educator"));
			roles.add(Role.valueOf("Student"));
		} else
			roles.add(role);

		if (startDate != null && endDate != null)
			dates = commonHelper.parseDateRange(startDate, endDate);

		if (sort != null) {
			Sort sorting = commonHelper.parseSortString(sort);
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);
		}

		Page<User> educators = userRepository.findUsersByFilters(roles, dates[0], dates[1], query, pageable);
		return commonHelper.getPageResponse(educators);
	}
}
