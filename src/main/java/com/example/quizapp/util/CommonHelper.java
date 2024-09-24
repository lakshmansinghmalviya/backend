package com.example.quizapp.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.PageResponse;
import com.example.quizapp.dto.UnifiedResponse;

@Service
public class CommonHelper {

	public <T> UnifiedResponse<PageResponse<T>> getPageResponse(Page<T> page) {
		PageResponse<T> pageResponse = new PageResponse<>();
		pageResponse.setContent(page.getContent());
		pageResponse.setPageNumber(page.getNumber());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setTotalElements(page.getTotalElements());
		pageResponse.setTotalPages(page.getTotalPages());
		return new UnifiedResponse<>(Codes.OK, "Fetched Successfully", pageResponse);
	}

	public Pageable makePageReq(int page, int size) {
		return PageRequest.of(page, size);
	}
}
