package ru.uniteam.models.exceptions;

public class UserHasTooManyProjectsException extends RuntimeException{
    public UserHasTooManyProjectsException(Long userId){
        super("The user with id =" + userId + " has too many projects");
    }
}
