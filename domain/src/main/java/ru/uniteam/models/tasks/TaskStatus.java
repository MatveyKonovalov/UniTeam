package ru.uniteam.models.tasks;

public enum TaskStatus {
    ACTIVE("Активная"),
    IN_PROGRESS("В прогрессе"),
    COMPLETED("Выполнена");

    private final String title;

    TaskStatus(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
