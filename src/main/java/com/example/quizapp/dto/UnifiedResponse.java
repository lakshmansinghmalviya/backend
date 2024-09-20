package com.example.quizapp.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnifiedResponse<T> implements Serializable {
	private Integer code;
	private String msg;
	private T data;
}