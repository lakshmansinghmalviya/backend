package com.example.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.entity.Option;
import com.example.quizapp.service.OptionService;

@RestController
@RequestMapping("/options")
public class OptionController {
	@Autowired
	OptionService optionService;
	
	@GetMapping("/questions/{id}")
    public ResponseEntity<List<Option>> getAllOptionByQuestionId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(optionService.getAllOptionByQuestionId(id));
    }
}
