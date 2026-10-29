package ru.uniteam.models.exceptions;

public class IncorrectTaskUndefinedException extends IllegalArgumentException{
    public IncorrectTaskUndefinedException(){
        super("The task has not found");
    }
}
