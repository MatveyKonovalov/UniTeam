package ru.uniteam.webplatform.data.entities.dto.tasks;


import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;

import java.time.LocalDateTime;

public record ProjectTaskGet(long id,
                             String title,
                             String description,
                             TaskStatus status,
                             TaskType taskType,
                             long projectId,
                             long userId,
                             LocalDateTime deadLine) {
}
