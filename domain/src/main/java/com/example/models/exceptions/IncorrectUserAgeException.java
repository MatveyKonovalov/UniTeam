package com.example.models.exceptions;

public class IncorrectUserAgeException extends RuntimeException{
    public IncorrectUserAgeException(){
        super("Incorrect user age");
    }
}
