package com.example.models.tasks;

public record ProjectTask(
        Long id,
        String title,
        String description,
        TaskStatus taskStatus,
        TaskType taskType
) { }
