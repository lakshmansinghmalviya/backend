package com.example.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizapp.dto.OptionRequest;
import com.example.quizapp.entity.Option;
import com.example.quizapp.entity.Question;
import com.example.quizapp.repository.OptionRepository;

@Service
public class OptionService {
	@Autowired
	OptionRepository optionRepository;

	public Option createOption(OptionRequest request, Question question) {
		Option option = new Option();
		option.setOptionPic(request.getOptionPic());
		option.setText(request.getText());
		option.setIsCorrect(request.getIsCorrect());
		option.setQuestion(question);
		return optionRepository.save(option);
	}

	public Option updateOption(OptionRequest request) {
		Option option = optionRepository.findById(request.getId())
				.orElseThrow(() -> new RuntimeException("Option not found with id: " + request.getId()));
		option.setText(request.getText());
		option.setOptionPic(request.getOptionPic());
		option.setIsCorrect(request.getIsCorrect());
		return optionRepository.save(option);
	}
}
