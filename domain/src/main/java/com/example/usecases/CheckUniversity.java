package com.example.usecases;

import com.example.models.University;

public class CheckUniversity {
    private static final String incorrectUniversityMessage = "The university must not be null";

    public static void checkUniversity(University university){
        if (university == null){
            throw new IllegalArgumentException(incorrectUniversityMessage);
        }
    }
}
