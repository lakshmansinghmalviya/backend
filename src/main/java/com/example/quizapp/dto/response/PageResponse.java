package com.example.quizapp.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageResponse<T> {
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
}
