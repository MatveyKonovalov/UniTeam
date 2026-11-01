package ru.uniteam.webplatform.data.entities.dto.tasks;

import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;

import java.time.LocalDateTime;

public record ProjectTaskInfo(
        long id,
        String title,
        String description,
        TaskStatus status,
        TaskType taskType,
        LocalDateTime deadLine
) {
}
