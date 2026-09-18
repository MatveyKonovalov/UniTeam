package com.example.models.tasks;

public enum TaskType{
    BACKEND("Бекенд"),
    FRONTEND("Фронтенд"),
    QA("Тестирование"),
    MOBILE("Мобильная разработка"),
    CYBERSECURITY("Информационная безопасность"),
    FULLSTACK("Фулл стэк"),
    ANALYTICS("Аналитика"),
    UX_UI("UX/UI дизайн"),
    INFRASTRUCTURE("Инфраструктура");

    private final String title;

    TaskType(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
