package com.example.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.OptionRequest;
import com.example.quizapp.entity.Option;
import com.example.quizapp.entity.Question;
import com.example.quizapp.exception.ResourceNotFoundException;
import com.example.quizapp.repository.OptionRepository;

@Service
public class OptionService {
	@Autowired
	OptionRepository optionRepository;

	public Option createOption(OptionRequest request, Question question) {
		try {
			Option option = new Option();
			option.setOptionPic(request.getOptionPic());
			option.setText(request.getText());
			option.setIsCorrect(request.getIsCorrect());
			option.setQuestion(question);
			return optionRepository.save(option);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create option " + e.getMessage());
		}
	}

	public List<Option> getAllOptionByQuestionId(Long id) {
		try {
			List<Option> options = optionRepository.findAllByQuestionId(id);
			if (!options.isEmpty())
				return options;
			else
				throw new ResourceNotFoundException("Option are not available in this question");
		} catch (Exception e) {
			throw new RuntimeException("Failed to find all options by the question id" + e.getMessage());
		}
	}
}
