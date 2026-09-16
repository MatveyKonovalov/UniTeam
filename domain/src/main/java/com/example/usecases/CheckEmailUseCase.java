package com.example.usecases;

import com.example.models.exceptions.IncorrectUserEmailException;

import java.util.regex.Pattern;

public final class CheckEmailUseCase {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static void checkEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()){
            throw new IncorrectUserEmailException();
        }
    }
}
