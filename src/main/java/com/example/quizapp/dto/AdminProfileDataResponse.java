package com.example.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileDataResponse {
    private Long totalUser;
    private Long totalCategory;
}
