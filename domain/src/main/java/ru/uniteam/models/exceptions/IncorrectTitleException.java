package ru.uniteam.models.exceptions;

public class IncorrectTitleException extends IllegalArgumentException{
    public IncorrectTitleException(){
        super("The title must be not null");
    }
}
