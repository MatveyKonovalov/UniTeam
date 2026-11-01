package ru.uniteam.models.exceptions;

public class IncorrectDescriptionException extends IllegalArgumentException{
    public IncorrectDescriptionException(){
        super("The description must be not null");
    }
}
