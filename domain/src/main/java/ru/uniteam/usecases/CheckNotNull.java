package ru.uniteam.usecases;

public class CheckNotNull {
    public static <T> void checkNotNull(T object, RuntimeException exception){
        if (object == null){
            throw exception;
        }
    }
}
