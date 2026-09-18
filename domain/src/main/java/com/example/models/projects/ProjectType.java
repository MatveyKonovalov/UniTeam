package com.example.models.projects;

public enum ProjectType {
    DEVELOPMENT("Разработка"),
    ANALYTICS("Аналитика"),
    ML("Машинное обучение"),
    INFORMATION_SECURITY("Информационная безопасность");

    private final String title;

    ProjectType(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
