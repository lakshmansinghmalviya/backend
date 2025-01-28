package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionRequest {

    @NotBlank(message = "Text cannot be blank.")
    @Size(min = 1, max = 300, message = "Text should be between 1 and 300 characters.")
    private String text;

    @NotNull(message = "IsCorrect should not be null.")
    private Boolean isCorrect;

    private Long id;

    private String optionPic;

    public OptionRequest() {
    }

    public OptionRequest(String text, Boolean isCorrect, Long id, String optionPic) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.id = id;
        this.optionPic = optionPic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionPic() {
        return optionPic;
    }

    public void setOptionPic(String optionPic) {
        this.optionPic = optionPic;
    }
}

