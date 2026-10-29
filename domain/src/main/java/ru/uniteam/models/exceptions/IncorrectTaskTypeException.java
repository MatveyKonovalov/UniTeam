package ru.uniteam.models.exceptions;

public class IncorrectTaskTypeException extends IllegalArgumentException{
    public IncorrectTaskTypeException(){
        super("The taskType must be not null");
    }
}
