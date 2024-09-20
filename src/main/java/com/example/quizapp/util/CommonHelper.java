package com.example.quizapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;
import com.example.quizapp.entity.User;
import com.example.quizapp.service.UserService;

@Service
public class CommonHelper {
	@Autowired
	UserService userService;

	public <T> UnifiedResponse<PageResponse<T>> getPageResponse(Page<T> page) {
		PageResponse<T> pageResponse = new PageResponse<>();
		pageResponse.setContent(page.getContent());
		pageResponse.setPageNumber(page.getNumber());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setTotalElements(page.getTotalElements());
		pageResponse.setTotalPages(page.getTotalPages());
		return new UnifiedResponse<>(HttpStatus.OK.value(), "Fetched Successfully", pageResponse);
	}

	public User getUser() {
		return userService.getUserInfoUsingTokenInfo();
	}
}
