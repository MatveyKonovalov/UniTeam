package com.example.models.users;

import com.example.models.University;
import com.example.usecases.CheckEmailUseCase;

public record UserCommonInfo(
        Long userId,
        String firstName,
        String lastName,
        String email,
        University university) {
    public UserCommonInfo {
        CheckEmailUseCase.checkEmail(email);
    }
}
