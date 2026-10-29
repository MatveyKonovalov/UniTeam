package ru.uniteam.models.exceptions;

public class IncorrectProjectRoleException extends IllegalArgumentException{
    public IncorrectProjectRoleException(){
        super("Incorrect project role");
    }
}
