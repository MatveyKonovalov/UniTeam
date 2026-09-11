package com.example.models.exceptions;

public class IncorrectUserEmailException extends RuntimeException{

    public IncorrectUserEmailException(){
        super("Incorrect user email.");
    }
}
