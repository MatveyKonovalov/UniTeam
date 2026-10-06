package ru.uniteam.models.exceptions;

public class IncorrectUserEmailException extends RuntimeException{

    public IncorrectUserEmailException(){
        super("Incorrect user email.");
    }
}
