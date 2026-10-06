package ru.uniteam.usecases;

import ru.uniteam.models.University;

public class CheckUniversity {
    private static final String incorrectUniversityMessage = "The university must not be null";

    public static void checkUniversity(University university){
        if (university == null){
            throw new IllegalArgumentException(incorrectUniversityMessage);
        }
    }
}
