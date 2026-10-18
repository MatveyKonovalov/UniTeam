package ru.uniteam.usecases;

public class CheckCourse {
    public static void checkCourse(int course){
        if (course < 1 || course > 6) {
            throw new IllegalArgumentException("The training course duration must be in the range of 1 to 6.");
        }
    }
}
