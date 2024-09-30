package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	public UnifiedResponse<PageResponse<User>> getEducators(Pageable pageable) {
		Role role = Role.valueOf("Educator");
		Page<User> userPage = userRepository.findByRole(role, pageable);
		return commonHelper.getPageResponse(userPage);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
	}

	public UnifiedResponse<User> updateUser(UpdateUserRequest request) {
		User user = getUserInfoUsingTokenInfo();
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

	public User getUserInfoUsingTokenInfo() {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return getUserByEmail(username);
	}

	public UnifiedResponse<User> getUserInformation() {
		return commonHelper.returnUnifiedOK("Fetched user", getUserInfoUsingTokenInfo());
	}

	public UnifiedResponse<EducatorProfileDataResponse> getEducatorProfileInformation() {
		User creator = getUserInfoUsingTokenInfo();
		Long totalCategory = categoryRepository.countByCreatorId(creator.getId());
		Long totalQuiz = quizRepository.countByCreatorId(creator.getId());
		Long totalQuestion = questionRepository.countByCreatorId(creator.getId());
		return commonHelper.returnUnifiedOK("Fetched user",
				new EducatorProfileDataResponse(totalCategory, totalQuiz, totalQuestion));
	}

	public UnifiedResponse<PageResponse<User>> searchEducatorsByQuery(String query, Pageable pageable) {
		Page<User> educators = userRepository.findByRoleAndNameContainingIgnoreCaseOrBioContainingIgnoreCase(
				Role.valueOf("Educator"), query, query, pageable);
		return commonHelper.getPageResponse(educators);
	}
}
