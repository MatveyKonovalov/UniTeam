package ru.uniteam.webplatform.data.entities.dto.tasks;

import ru.uniteam.models.exceptions.IncorrectDescriptionException;
import ru.uniteam.models.exceptions.IncorrectTaskTypeException;
import ru.uniteam.models.exceptions.IncorrectTitleException;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import ru.uniteam.usecases.CheckNotNull;

import java.time.LocalDateTime;

public record  ProjectTaskPost(
        String title,
        String description,
        TaskType taskType,
        long projectId,
        long userId,
        LocalDateTime deadLine
) {
    public ProjectTaskPost{
        CheckNotNull.checkNotNull(title, new IncorrectTitleException());
        CheckNotNull.checkNotNull(description, new IncorrectDescriptionException());
        CheckNotNull.checkNotNull(taskType, new IncorrectTaskTypeException());
    }
}
