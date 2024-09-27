package com.example.quizapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	public <T> UnifiedResponse<T> returnUnifiedOK(String message, T data) {
		return new UnifiedResponse<>(Codes.OK, message, data);
	}

	public <T> UnifiedResponse<T> returnUnifiedCREATED(String message, T data) {
		return new UnifiedResponse<>(Codes.CREATED, message, data);
	}

	public Pageable makePageReq(int page, int size) {
		return PageRequest.of(page, size);
	}

	public LocalDateTime[] parseDateRange(String startDate, String endDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
		LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);
		return new LocalDateTime[] { startDateTime, endDateTime };
	}
}
