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

    public AdminProfileDataResponse() {
    }

    public AdminProfileDataResponse(Long totalUser, Long totalCategory) {
        this.totalUser = totalUser;
        this.totalCategory = totalCategory;
    }

    public Long getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Long totalUser) {
        this.totalUser = totalUser;
    }

    public Long getTotalCategory() {
        return totalCategory;
    }

    public void setTotalCategory(Long totalCategory) {
        this.totalCategory = totalCategory;
    }
}
