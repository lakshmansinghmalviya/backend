package com.example.quizapp.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

    private Long quizId;

    @NotBlank(message = "Question type cannot be blank.")
    private String questionType;

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 3, max = 300, message = "Question text should be between 3 and 300 characters.")
    private String text;

    @NotNull(message = "Max score should not be null.")
    @Min(value = 1, message = "Max score must be at least 1 and max 10")
    @Max(value = 10, message = "Max score must be less than 11")
    private Long maxScore;

    private String questionPic;

    private Boolean randomizeOptions;

    @Size(min = 2, message = "At least two options must be provided.")
    private List<OptionRequest> options;

    public QuestionRequest() {
    }

    public QuestionRequest(Long quizId, String questionType, String text, Long maxScore, String questionPic, Boolean randomizeOptions, List<OptionRequest> options) {
        this.quizId = quizId;
        this.questionType = questionType;
        this.text = text;
        this.maxScore = maxScore;
        this.questionPic = questionPic;
        this.randomizeOptions = randomizeOptions;
        this.options = options;
    }

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Long maxScore) {
		this.maxScore = maxScore;
	}

	public String getQuestionPic() {
		return questionPic;
	}

	public void setQuestionPic(String questionPic) {
		this.questionPic = questionPic;
	}

	public Boolean getRandomizeOptions() {
		return randomizeOptions;
	}

	public void setRandomizeOptions(Boolean randomizeOptions) {
		this.randomizeOptions = randomizeOptions;
	}

	public List<OptionRequest> getOptions() {
		return options;
	}

	public void setOptions(List<OptionRequest> options) {
		this.options = options;
	}
 
}

