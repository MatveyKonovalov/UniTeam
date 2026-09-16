package com.example.models.commoninfo;

import com.example.models.University;
import com.example.models.exceptions.IncorrectUserEmailException;
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
